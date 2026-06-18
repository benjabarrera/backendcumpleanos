package com.micumpleanos.personal.application.dto.asignacion;

import com.micumpleanos.personal.domain.enums.EstadoAsignacion;

import java.time.LocalTime;

public record AsignacionPersonalResponse(
        Long idAsignacion,
        Long idEvento,
        String eventoNombre,
        Long idPersonal,
        String personalNombre,
        String rolPersonal,
        LocalTime horaEntrada,
        LocalTime horaSalida,
        LocalTime horaEntradaReal,
        LocalTime horaSalidaReal,
        EstadoAsignacion estado,
        String notas
) {
}
