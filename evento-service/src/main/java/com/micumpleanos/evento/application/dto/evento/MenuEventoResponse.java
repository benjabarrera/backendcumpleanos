package com.micumpleanos.evento.application.dto.evento;

import com.micumpleanos.evento.domain.enums.TipoPinata;

public record MenuEventoResponse(
        Long idMenu,
        Integer platosDulces,
        Integer platosSalados,
        Integer tortas,
        Integer bolsasSorpresa,
        TipoPinata tipoPinata,
        Integer cantidadPinatas,
        String notasMenu
) {
}
