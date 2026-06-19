package com.micumpleanos.auth.application.dto.usuario;

import com.micumpleanos.auth.domain.enums.RolSistema;

import java.time.LocalDateTime;

public record UsuarioSistemaResponse(
        Long idUsuario,
        Long idPersonal,
        String personalNombre,
        String username,
        RolSistema rolSistema,
        Boolean activo,
        LocalDateTime ultimoLogin
) {
}
