package com.micumpleanos.personal.application.service;

import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalRequest;
import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalResponse;
import com.micumpleanos.personal.domain.enums.EstadoAsignacion;

import java.util.List;

public interface AsignacionPersonalService {
    AsignacionPersonalResponse crear(AsignacionPersonalRequest request);
    AsignacionPersonalResponse actualizar(Long idAsignacion, AsignacionPersonalRequest request);
    AsignacionPersonalResponse buscarPorId(Long idAsignacion);
    List<AsignacionPersonalResponse> listar();
    List<AsignacionPersonalResponse> listarPorEvento(Long idEvento);
    List<AsignacionPersonalResponse> listarPorEstado(EstadoAsignacion estado);
    void eliminar(Long idAsignacion);
}
