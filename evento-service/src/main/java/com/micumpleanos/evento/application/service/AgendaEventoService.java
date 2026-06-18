package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.agenda.AgendaEventoRequest;
import com.micumpleanos.evento.application.dto.agenda.AgendaEventoResponse;

import java.util.List;

public interface AgendaEventoService {
    AgendaEventoResponse crear(AgendaEventoRequest request);
    AgendaEventoResponse actualizar(Long idAgenda, AgendaEventoRequest request);
    AgendaEventoResponse buscarPorId(Long idAgenda);
    AgendaEventoResponse buscarPorEvento(Long idEvento);
    List<AgendaEventoResponse> listar();
    void eliminar(Long idAgenda);
}
