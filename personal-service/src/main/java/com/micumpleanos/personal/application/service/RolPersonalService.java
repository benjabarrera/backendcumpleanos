package com.micumpleanos.personal.application.service;

import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalRequest;
import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalResponse;

import java.util.List;

public interface RolPersonalService {
    RolPersonalResponse crear(RolPersonalRequest request);
    RolPersonalResponse actualizar(Long idRol, RolPersonalRequest request);
    RolPersonalResponse buscarPorId(Long idRol);
    List<RolPersonalResponse> listar();
    void eliminar(Long idRol);
}
