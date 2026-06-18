package com.micumpleanos.evento.application.dto.evento;

import com.micumpleanos.evento.domain.enums.EstadoEvento;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoCreateRequest(
        @NotNull long idCliente,
        @NotNull long idTipoFiesta,
        @NotBlank @Size(max = 100) String nombreCelebrado,
        @NotNull LocalDate fechaEvento,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFin,
        @NotBlank @Size(max = 255) String lugar,
        @NotNull @Min(10) @Max(40) Integer cantidadNinos,
        EstadoEvento estado,
        String observaciones
) {
}
