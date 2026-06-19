package com.micumpleanos.personal.application.dto.asignacion;

import com.micumpleanos.personal.domain.enums.EstadoAsignacion;

import java.time.LocalDate;
import java.time.LocalTime;

public record AsignacionPersonalResponse(
        Long idAsignacion,
        Long idEvento,
        String eventoNombre,
        Long idPersonal,
        String nombrePersonal,
        String rolPersonal,
        LocalDate fechaAsignacion,
        LocalTime horaEntrada,
        LocalTime horaSalida,
        LocalTime horaEntradaReal,
        LocalTime horaSalidaReal,
        EstadoAsignacion estado,
        String notas
) {
}
