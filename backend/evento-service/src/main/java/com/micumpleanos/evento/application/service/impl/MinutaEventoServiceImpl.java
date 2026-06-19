package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.minuta.MinutaEventoRequest;
import com.micumpleanos.evento.application.dto.minuta.MinutaEventoResponse;
import com.micumpleanos.evento.application.service.MinutaEventoService;
import com.micumpleanos.evento.domain.entity.Evento;
import com.micumpleanos.evento.domain.entity.MinutaEvento;
import com.micumpleanos.evento.domain.repository.EventoRepository;
import com.micumpleanos.evento.domain.repository.MinutaEventoRepository;
import com.micumpleanos.evento.shared.exception.BusinessRuleException;
import com.micumpleanos.evento.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MinutaEventoServiceImpl implements MinutaEventoService {

    private final MinutaEventoRepository minutaEventoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public MinutaEventoResponse crear(MinutaEventoRequest request) {
        if (minutaEventoRepository.findByEvento_IdEvento(request.idEvento()).isPresent()) {
            throw new BusinessRuleException("Ya existe una minuta para este evento.");
        }
        return toResponse(minutaEventoRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public MinutaEventoResponse actualizar(Long idMinuta, MinutaEventoRequest request) {
        MinutaEvento minuta = getById(idMinuta);
        aplicar(minuta, request);
        return toResponse(minutaEventoRepository.save(Objects.requireNonNull(minuta)));
    }

    @Override
    @Transactional(readOnly = true)
    public MinutaEventoResponse buscarPorId(Long idMinuta) {
        return toResponse(getById(idMinuta));
    }

    @Override
    @Transactional(readOnly = true)
    public MinutaEventoResponse buscarPorEvento(Long idEvento) {
        return toResponse(minutaEventoRepository.findByEvento_IdEvento(Objects.requireNonNull(idEvento))
                .orElseThrow(() -> new ResourceNotFoundException("Minuta no encontrada para evento: " + idEvento)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MinutaEventoResponse> listar() {
        return minutaEventoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idMinuta) {
        minutaEventoRepository.delete(Objects.requireNonNull(getById(idMinuta)));
    }

    private MinutaEvento getById(Long idMinuta) {
        return minutaEventoRepository.findById(Objects.requireNonNull(idMinuta))
                .orElseThrow(() -> new ResourceNotFoundException("Minuta no encontrada: " + idMinuta));
    }

    private MinutaEvento toEntity(MinutaEventoRequest request) {
        MinutaEvento minuta = new MinutaEvento();
        aplicar(minuta, request);
        return minuta;
    }

    private void aplicar(MinutaEvento minuta, MinutaEventoRequest request) {
        Evento evento = eventoRepository.findById(Objects.requireNonNull(request.idEvento()))
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado: " + request.idEvento()));
        minuta.setEvento(evento);
        minuta.setNumeroMinuta(request.numeroMinuta());
        minuta.setFechaEmision(request.fechaEmision());
        minuta.setContenidoHtml(request.contenidoHtml());
        if (request.enviadaCliente() != null) {
            minuta.setEnviadaCliente(request.enviadaCliente());
        }
        minuta.setFechaEnvio(request.fechaEnvio());
        if (request.firmaCliente() != null) {
            minuta.setFirmaCliente(request.firmaCliente());
        }
    }

    private MinutaEventoResponse toResponse(MinutaEvento minuta) {
        return new MinutaEventoResponse(
                minuta.getIdMinuta(),
                minuta.getEvento().getIdEvento(),
                minuta.getEvento().getNombreCelebrado(),
                minuta.getNumeroMinuta(),
                minuta.getFechaEmision(),
                minuta.getContenidoHtml(),
                minuta.getEnviadaCliente(),
                minuta.getFechaEnvio(),
                minuta.getFirmaCliente()
        );
    }
}
