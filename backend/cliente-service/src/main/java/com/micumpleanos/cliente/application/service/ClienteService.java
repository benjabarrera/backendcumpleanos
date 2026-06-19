package com.micumpleanos.cliente.application.service;

import com.micumpleanos.cliente.application.dto.cliente.ClienteRequest;
import com.micumpleanos.cliente.application.dto.cliente.ClienteResponse;

import java.util.List;

public interface ClienteService {
    ClienteResponse crear(ClienteRequest request);
    ClienteResponse actualizar(Long idCliente, ClienteRequest request);
    ClienteResponse buscarPorId(Long idCliente);
    List<ClienteResponse> listar();
    void eliminar(Long idCliente);
}
