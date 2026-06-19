package com.micumpleanos.evento.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionDTO {
    private Integer cantidadNinos;
    private Integer insumosNecesarios;
    private Double costoInsumos;
    private Double costoBase;
    private Double presupuestoTotal;
}
