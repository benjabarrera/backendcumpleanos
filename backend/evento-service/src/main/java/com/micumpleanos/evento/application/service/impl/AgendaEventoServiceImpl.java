package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.agenda.AgendaEventoRequest;
import com.micumpleanos.evento.application.dto.agenda.AgendaEventoResponse;
import com.micumpleanos.evento.application.service.AgendaEventoService;
import com.micumpleanos.evento.domain.entity.AgendaEvento;
import com.micumpleanos.evento.domain.entity.Evento;
import com.micumpleanos.evento.domain.repository.AgendaEventoRepository;
import com.micumpleanos.evento.domain.repository.EventoRepository;
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
public class AgendaEventoServiceImpl implements AgendaEventoService {

    private final AgendaEventoRepository agendaEventoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public AgendaEventoResponse crear(AgendaEventoRequest request) {
        if (agendaEventoRepository.findByEvento_IdEvento(request.idEvento()).isPresent()) {
            throw new BusinessRuleException("Ya existe una agenda para este evento.");
        }
        return toResponse(agendaEventoRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public AgendaEventoResponse actualizar(Long idAgenda, AgendaEventoRequest request) {
        AgendaEvento agenda = getById(idAgenda);
        aplicar(agenda, request);
        return toResponse(agendaEventoRepository.save(Objects.requireNonNull(agenda)));
    }

    @Override
    @Transactional(readOnly = true)
    public AgendaEventoResponse buscarPorId(Long idAgenda) {
        return toResponse(getById(idAgenda));
    }

    @Override
    @Transactional(readOnly = true)
    public AgendaEventoResponse buscarPorEvento(Long idEvento) {
        return toResponse(agendaEventoRepository.findByEvento_IdEvento(Objects.requireNonNull(idEvento))
                .orElseThrow(() -> new ResourceNotFoundException("Agenda no encontrada para evento: " + idEvento)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgendaEventoResponse> listar() {
        return agendaEventoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idAgenda) {
        agendaEventoRepository.delete(Objects.requireNonNull(getById(idAgenda)));
    }

    private AgendaEvento getById(Long idAgenda) {
        return agendaEventoRepository.findById(Objects.requireNonNull(idAgenda))
                .orElseThrow(() -> new ResourceNotFoundException("Agenda no encontrada: " + idAgenda));
    }

    private AgendaEvento toEntity(AgendaEventoRequest request) {
        AgendaEvento agenda = new AgendaEvento();
        aplicar(agenda, request);
        return agenda;
    }

    private void aplicar(AgendaEvento agenda, AgendaEventoRequest request) {
        Evento evento = eventoRepository.findById(Objects.requireNonNull(request.idEvento()))
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado: " + request.idEvento()));
        agenda.setEvento(evento);
        agenda.setHoraMontaje(request.horaMontaje());
        agenda.setHoraBienvenida(request.horaBienvenida());
        agenda.setHoraJuegos(request.horaJuegos());
        agenda.setHoraTorta(request.horaTorta());
        agenda.setHoraDesmontaje(request.horaDesmontaje());
        agenda.setDetalles(request.detalles());
    }

    private AgendaEventoResponse toResponse(AgendaEvento agenda) {
        return new AgendaEventoResponse(
                agenda.getIdAgenda(),
                agenda.getEvento().getIdEvento(),
                agenda.getHoraMontaje(),
                agenda.getHoraBienvenida(),
                agenda.getHoraJuegos(),
                agenda.getHoraTorta(),
                agenda.getHoraDesmontaje(),
                agenda.getDetalles()
        );
    }
}
