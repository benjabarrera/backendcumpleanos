package com.micumpleanos.personal.application.dto.rolpersonal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RolPersonalRequest(
        @NotBlank @Size(max = 60) String nombre,
        String descripcion,
        BigDecimal tarifaHora
) {
}
