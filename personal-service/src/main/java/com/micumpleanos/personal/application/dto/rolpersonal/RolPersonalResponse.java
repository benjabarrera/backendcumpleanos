package com.micumpleanos.personal.application.dto.rolpersonal;

import java.math.BigDecimal;

public record RolPersonalResponse(
        Long idRol,
        String nombre,
        String descripcion,
        BigDecimal tarifaHora
) {
}
