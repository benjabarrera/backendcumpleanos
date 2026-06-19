package com.micumpleanos.auth.application.service;

import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaRequest;
import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaResponse;

import java.util.List;

public interface UsuarioSistemaService {
    UsuarioSistemaResponse crear(UsuarioSistemaRequest request);
    UsuarioSistemaResponse actualizar(Long idUsuario, UsuarioSistemaRequest request);
    UsuarioSistemaResponse buscarPorId(Long idUsuario);
    UsuarioSistemaResponse buscarPorUsername(String username);
    List<UsuarioSistemaResponse> listar();
    void eliminar(Long idUsuario);
}
