package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.InsumoDTO;
import com.micumpleanos.evento.application.dto.MovimientoDTO;

import java.util.List;

public interface InsumoService {
    InsumoDTO crearInsumo(InsumoDTO insumoDTO);
    InsumoDTO registrarIngreso(MovimientoDTO movimientoDTO);
    List<InsumoDTO> listarInsumos();
}
