package com.micumpleanos.evento.application.dto.agenda;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record AgendaEventoRequest(
        @NotNull Long idEvento,
        @NotNull LocalTime horaMontaje,
        @NotNull LocalTime horaBienvenida,
        @NotNull LocalTime horaJuegos,
        @NotNull LocalTime horaTorta,
        @NotNull LocalTime horaDesmontaje,
        String detalles
) {
}
