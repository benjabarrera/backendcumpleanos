package com.micumpleanos.evento.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDTO {
    private Long idInsumo;
    private String nombre;
    private Integer stockActual;
    private Integer stockCriticoUmbral;
    private Boolean alertaStockCritico;
}
