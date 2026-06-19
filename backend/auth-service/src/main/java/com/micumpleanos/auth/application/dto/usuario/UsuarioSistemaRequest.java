package com.micumpleanos.auth.application.dto.usuario;

import com.micumpleanos.auth.domain.enums.RolSistema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UsuarioSistemaRequest(
        Long idPersonal,
        @NotBlank @Size(max = 60) String username,
        @NotBlank @Size(max = 255) String passwordHash,
        RolSistema rolSistema,
        Boolean activo,
        LocalDateTime ultimoLogin
) {
}
