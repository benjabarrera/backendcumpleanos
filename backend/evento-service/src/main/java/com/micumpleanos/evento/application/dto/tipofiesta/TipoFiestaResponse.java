package com.micumpleanos.evento.application.dto.tipofiesta;

public record TipoFiestaResponse(
        Long idTipoFiesta,
        String nombre,
        String descripcion,
        String colorHex,
        Boolean activo
) {
}
