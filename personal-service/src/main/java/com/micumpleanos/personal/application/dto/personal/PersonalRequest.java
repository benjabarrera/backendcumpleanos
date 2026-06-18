package com.micumpleanos.personal.application.dto.personal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonalRequest(
        @NotNull Long idRol,
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 100) String apellido,
        @Email @NotBlank @Size(max = 150) String email,
        @Size(max = 20) String telefono,
        @Size(max = 12) String rut,
        Boolean activo
) {
}
