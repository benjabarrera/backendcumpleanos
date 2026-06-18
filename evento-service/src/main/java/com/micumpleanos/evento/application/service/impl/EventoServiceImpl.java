package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.evento.EventoCreateRequest;
import com.micumpleanos.evento.application.dto.evento.EventoResponse;
import com.micumpleanos.evento.application.dto.evento.EventoUpdateRequest;
import com.micumpleanos.evento.application.dto.evento.MenuEventoResponse;
import com.micumpleanos.evento.application.service.EventoService;
import com.micumpleanos.evento.domain.entity.Evento;
import com.micumpleanos.evento.domain.entity.MenuEvento;
import com.micumpleanos.evento.domain.entity.TipoFiesta;
import com.micumpleanos.evento.domain.enums.EstadoEvento;
import com.micumpleanos.evento.domain.enums.TipoPinata;
import com.micumpleanos.evento.domain.repository.EventoRepository;
import com.micumpleanos.evento.domain.repository.MenuEventoRepository;
import com.micumpleanos.evento.domain.repository.TipoFiestaRepository;
import com.micumpleanos.evento.shared.exception.BusinessRuleException;
import com.micumpleanos.evento.shared.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final TipoFiestaRepository tipoFiestaRepository;
    private final MenuEventoRepository menuEventoRepository;

    @Override
    public EventoResponse crearEvento(EventoCreateRequest request) {
        TipoFiesta tipoFiesta = tipoFiestaRepository.findById(request.idTipoFiesta())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de fiesta no encontrado: " + request.idTipoFiesta()));

        Evento evento = Evento.builder()
                .idCliente(request.idCliente())
                .tipoFiesta(tipoFiesta)
                .nombreCelebrado(request.nombreCelebrado())
                .fechaEvento(request.fechaEvento())
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .lugar(request.lugar())
                .cantidadNinos(request.cantidadNinos())
                .estado(request.estado() != null ? request.estado() : EstadoEvento.SOLICITADO)
                .observaciones(request.observaciones())
                .build();

        validarReglasDeHorario(evento);
        Evento saved = eventoRepository.save(Objects.requireNonNull(evento));

        if (saved.getEstado() == EstadoEvento.CONFIRMADO) {
            generarOActualizarMenu(saved);
        }

        return toResponse(refreshEvento(saved.getIdEvento()));
    }

    @Override
    public EventoResponse actualizarEvento(long idEvento, EventoUpdateRequest request) {
        Evento evento = getEventoOrFail(idEvento);

        if (!Objects.equals(evento.getIdCliente(), request.idCliente())) {
            evento.setIdCliente(request.idCliente());
        }
        
        TipoFiesta tipoFiesta = tipoFiestaRepository.findById(request.idTipoFiesta())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de fiesta no encontrado: " + request.idTipoFiesta()));

        EstadoEvento estadoAnterior = evento.getEstado();

        evento.setTipoFiesta(tipoFiesta);
        evento.setNombreCelebrado(request.nombreCelebrado());
        evento.setFechaEvento(request.fechaEvento());
        evento.setHoraInicio(request.horaInicio());
        evento.setHoraFin(request.horaFin());
        evento.setLugar(request.lugar());
        evento.setCantidadNinos(request.cantidadNinos());
        evento.setEstado(request.estado());
        evento.setObservaciones(request.observaciones());

        validarReglasDeHorario(evento);
        Evento saved = eventoRepository.save(Objects.requireNonNull(evento));

        if (saved.getEstado() == EstadoEvento.CONFIRMADO && estadoAnterior != EstadoEvento.CONFIRMADO) {
            generarOActualizarMenu(saved);
        }

        return toResponse(refreshEvento(saved.getIdEvento()));
    }

    @Override
    public EventoResponse confirmarEvento(long idEvento) {
        Evento evento = getEventoOrFail(idEvento);
        evento.setEstado(EstadoEvento.CONFIRMADO);
        Evento saved = eventoRepository.save(evento);
        generarOActualizarMenu(saved);
        return toResponse(refreshEvento(saved.getIdEvento()));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public EventoResponse buscarPorId(long idEvento) {
        return toResponse(refreshEvento(idEvento));
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<EventoResponse> listarTodos() {
        return eventoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<EventoResponse> buscarPorEstado(EstadoEvento estado) {
        return eventoRepository.findAllByEstado(estado).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<EventoResponse> buscarPorFecha(LocalDate fechaEvento) {
        return eventoRepository.findAllByFechaEvento(fechaEvento).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void eliminarEvento(long idEvento) {
        Evento evento = getEventoOrFail(idEvento);
        eventoRepository.delete(Objects.requireNonNull(evento));
    }

    private Evento refreshEvento(long idEvento) {
        return eventoRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado: " + idEvento));
    }

    private Evento getEventoOrFail(long idEvento) {
        return eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado: " + idEvento));
    }

    private void validarReglasDeHorario(Evento evento) {
        if (!evento.getHoraFin().isAfter(evento.getHoraInicio())) {
            throw new BusinessRuleException("La hora de fin debe ser posterior a la hora de inicio.");
        }
    }

    private void generarOActualizarMenu(Evento evento) {
        int n = evento.getCantidadNinos();
        MenuEvento menu = menuEventoRepository.findByEvento_IdEvento(evento.getIdEvento())
                .orElseGet(() -> MenuEvento.builder().evento(evento).build());

        menu.setEvento(evento);
        menu.setPlatosDulces(n * 3);
        menu.setPlatosSalados(n * 2);
        menu.setTortas(n);
        menu.setBolsasSorpresa(n);
        menu.setTipoPinata(calcularTipoPinata(n));
        menu.setCantidadPinatas(1);
        menu.setNotasMenu("Calculado automáticamente por el motor de negocio.");

        menuEventoRepository.save(menu);
    }

    private TipoPinata calcularTipoPinata(int cantidadNinos) {
        if (cantidadNinos >= 10 && cantidadNinos <= 15) {
            return TipoPinata.PEQUEÑA;
        }
        if (cantidadNinos <= 25) {
            return TipoPinata.MEDIANA;
        }
        return TipoPinata.GRANDE;
    }

    private EventoResponse toResponse(Evento evento) {
        MenuEventoResponse menuResponse = null;
        if (evento.getMenuEvento() != null) {
            MenuEvento menu = evento.getMenuEvento();
            menuResponse = new MenuEventoResponse(
                    menu.getIdMenu(),
                    menu.getPlatosDulces(),
                    menu.getPlatosSalados(),
                    menu.getTortas(),
                    menu.getBolsasSorpresa(),
                    menu.getTipoPinata(),
                    menu.getCantidadPinatas(),
                    menu.getNotasMenu()
            );
        }

        return new EventoResponse(
                evento.getIdEvento(),
                evento.getIdCliente(),
                null, // Nombre del cliente no disponible sin Feign
                evento.getTipoFiesta().getIdTipoFiesta(),
                evento.getTipoFiesta().getNombre(),
                evento.getNombreCelebrado(),
                evento.getFechaEvento(),
                evento.getHoraInicio(),
                evento.getHoraFin(),
                evento.getLugar(),
                evento.getCantidadNinos(),
                evento.getEstado(),
                evento.getObservaciones(),
                menuResponse
        );
    }
}
