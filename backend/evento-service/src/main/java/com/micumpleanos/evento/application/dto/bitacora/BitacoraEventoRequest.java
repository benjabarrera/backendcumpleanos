package com.micumpleanos.evento.application.dto.bitacora;

import com.micumpleanos.evento.domain.enums.TipoRegistroBitacora;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BitacoraEventoRequest(
        @NotNull Long idEvento,
        Long idPersonal,
        @NotNull LocalDateTime timestampLog,
        TipoRegistroBitacora tipoRegistro,
        @NotNull String descripcion
) {
}
