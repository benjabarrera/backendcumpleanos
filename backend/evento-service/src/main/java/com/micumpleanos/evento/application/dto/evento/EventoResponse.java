package com.micumpleanos.evento.application.dto.evento;

import com.micumpleanos.evento.domain.enums.EstadoEvento;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoResponse(
        Long idEvento,
        Long idCliente,
        String clienteNombre,
        Long idTipoFiesta,
        String tipoFiestaNombre,
        String nombreCelebrado,
        LocalDate fechaEvento,
        LocalTime horaInicio,
        LocalTime horaFin,
        String lugar,
        Integer cantidadNinos,
        EstadoEvento estado,
        String observaciones,
        MenuEventoResponse menuEvento
) {
}
