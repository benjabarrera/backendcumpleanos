package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.CotizacionDTO;
import com.micumpleanos.evento.application.service.CotizacionService;
import org.springframework.stereotype.Service;

@Service
public class CotizacionServiceImpl implements CotizacionService {

    private static final Double COSTO_BASE = 50000.0;
    private static final Double COSTO_POR_INSUMO = 2000.0;
    private static final Integer INSUMOS_POR_NINO = 3;

    @Override
    public CotizacionDTO calcularCotizacion(Integer cantidadNinos) {
        if (cantidadNinos == null || cantidadNinos < 0) {
            throw new IllegalArgumentException("La cantidad de niños debe ser un número positivo.");
        }

        int insumosNecesarios = cantidadNinos * INSUMOS_POR_NINO;
        double costoInsumos = insumosNecesarios * COSTO_POR_INSUMO;
        double presupuestoTotal = COSTO_BASE + costoInsumos;

        return CotizacionDTO.builder()
                .cantidadNinos(cantidadNinos)
                .insumosNecesarios(insumosNecesarios)
                .costoInsumos(costoInsumos)
                .costoBase(COSTO_BASE)
                .presupuestoTotal(presupuestoTotal)
                .build();
    }
}
