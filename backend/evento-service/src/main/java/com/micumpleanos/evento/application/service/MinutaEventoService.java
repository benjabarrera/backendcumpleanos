package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.minuta.MinutaEventoRequest;
import com.micumpleanos.evento.application.dto.minuta.MinutaEventoResponse;

import java.util.List;

public interface MinutaEventoService {
    MinutaEventoResponse crear(MinutaEventoRequest request);
    MinutaEventoResponse actualizar(Long idMinuta, MinutaEventoRequest request);
    MinutaEventoResponse buscarPorId(Long idMinuta);
    MinutaEventoResponse buscarPorEvento(Long idEvento);
    List<MinutaEventoResponse> listar();
    void eliminar(Long idMinuta);
}
