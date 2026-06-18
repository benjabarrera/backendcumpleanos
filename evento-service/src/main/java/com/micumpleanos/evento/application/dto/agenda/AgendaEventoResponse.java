package com.micumpleanos.evento.application.dto.agenda;

import java.time.LocalTime;

public record AgendaEventoResponse(
        Long idAgenda,
        Long idEvento,
        LocalTime horaMontaje,
        LocalTime horaBienvenida,
        LocalTime horaJuegos,
        LocalTime horaTorta,
        LocalTime horaDesmontaje,
        String detalles
) {
}
