package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoRequest;
import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoResponse;

import java.util.List;

public interface BitacoraEventoService {
    BitacoraEventoResponse crear(BitacoraEventoRequest request);
    BitacoraEventoResponse actualizar(Long idBitacora, BitacoraEventoRequest request);
    BitacoraEventoResponse buscarPorId(Long idBitacora);
    List<BitacoraEventoResponse> listar();
    List<BitacoraEventoResponse> listarPorEvento(Long idEvento);
    void eliminar(Long idBitacora);
}
