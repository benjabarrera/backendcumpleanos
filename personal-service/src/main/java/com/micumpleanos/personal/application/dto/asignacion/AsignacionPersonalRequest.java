package com.micumpleanos.personal.application.dto.asignacion;

import com.micumpleanos.personal.domain.enums.EstadoAsignacion;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record AsignacionPersonalRequest(
        @NotNull Long idEvento,
        @NotNull Long idPersonal,
        LocalTime horaEntrada,
        LocalTime horaSalida,
        LocalTime horaEntradaReal,
        LocalTime horaSalidaReal,
        EstadoAsignacion estado,
        String notas
) {
}
