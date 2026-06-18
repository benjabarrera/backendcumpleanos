package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.CotizacionDTO;

public interface CotizacionService {
    CotizacionDTO calcularCotizacion(Integer cantidadNinos);
}
