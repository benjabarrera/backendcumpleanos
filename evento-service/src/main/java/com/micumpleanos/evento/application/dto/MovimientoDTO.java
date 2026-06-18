package com.micumpleanos.evento.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private Long idInsumo;
    private Integer cantidad;
    private String observacion;
}
