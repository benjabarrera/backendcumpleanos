package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.evento.EventoCreateRequest;
import com.micumpleanos.evento.application.dto.evento.EventoResponse;
import com.micumpleanos.evento.application.dto.evento.EventoUpdateRequest;
import com.micumpleanos.evento.domain.enums.EstadoEvento;

import java.time.LocalDate;
import java.util.List;

public interface EventoService {
    EventoResponse crearEvento(EventoCreateRequest request);

    EventoResponse actualizarEvento(long idEvento, EventoUpdateRequest request);

    EventoResponse confirmarEvento(long idEvento);

    EventoResponse buscarPorId(long idEvento);

    List<EventoResponse> listarTodos();

    List<EventoResponse> buscarPorEstado(EstadoEvento estado);

    List<EventoResponse> buscarPorFecha(LocalDate fechaEvento);

    void eliminarEvento(long idEvento);
}
