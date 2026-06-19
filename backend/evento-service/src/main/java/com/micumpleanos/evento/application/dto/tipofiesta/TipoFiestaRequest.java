package com.micumpleanos.evento.application.dto.tipofiesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoFiestaRequest(
        @NotBlank @Size(max = 80) String nombre,
        String descripcion,
        @Size(max = 7) String colorHex,
        Boolean activo
) {
}
