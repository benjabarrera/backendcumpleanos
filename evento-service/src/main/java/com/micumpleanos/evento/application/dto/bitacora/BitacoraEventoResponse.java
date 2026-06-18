package com.micumpleanos.evento.application.dto.bitacora;

import com.micumpleanos.evento.domain.enums.TipoRegistroBitacora;

import java.time.LocalDateTime;

public record BitacoraEventoResponse(
        Long idBitacora,
        Long idEvento,
        String eventoNombre,
        Long idPersonal,
        String personalNombre,
        LocalDateTime timestampLog,
        TipoRegistroBitacora tipoRegistro,
        String descripcion
) {
}
