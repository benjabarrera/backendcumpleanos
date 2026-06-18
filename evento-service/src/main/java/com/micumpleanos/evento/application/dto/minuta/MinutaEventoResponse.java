package com.micumpleanos.evento.application.dto.minuta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MinutaEventoResponse(
        Long idMinuta,
        Long idEvento,
        String eventoNombre,
        String numeroMinuta,
        LocalDate fechaEmision,
        String contenidoHtml,
        Boolean enviadaCliente,
        LocalDateTime fechaEnvio,
        Boolean firmaCliente
) {
}
