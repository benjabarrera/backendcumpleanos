package com.micumpleanos.evento.application.dto.minuta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MinutaEventoRequest(
        @NotNull Long idEvento,
        @NotBlank String numeroMinuta,
        @NotNull LocalDate fechaEmision,
        String contenidoHtml,
        Boolean enviadaCliente,
        LocalDateTime fechaEnvio,
        Boolean firmaCliente
) {
}
