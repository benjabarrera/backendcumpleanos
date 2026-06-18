package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaRequest;
import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaResponse;

import java.util.List;

public interface TipoFiestaService {
    TipoFiestaResponse crear(TipoFiestaRequest request);
    TipoFiestaResponse actualizar(Long idTipoFiesta, TipoFiestaRequest request);
    TipoFiestaResponse buscarPorId(Long idTipoFiesta);
    List<TipoFiestaResponse> listar();
    void eliminar(Long idTipoFiesta);
}
