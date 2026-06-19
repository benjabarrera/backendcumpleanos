package com.micumpleanos.cliente.application.service.impl;

import com.micumpleanos.cliente.application.dto.cliente.ClienteRequest;
import com.micumpleanos.cliente.application.dto.cliente.ClienteResponse;
import com.micumpleanos.cliente.application.service.ClienteService;
import com.micumpleanos.cliente.domain.entity.Cliente;
import com.micumpleanos.cliente.domain.repository.ClienteRepository;
import com.micumpleanos.cliente.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public ClienteResponse crear(ClienteRequest request) {
        return toResponse(clienteRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public ClienteResponse actualizar(Long idCliente, ClienteRequest request) {
        Cliente cliente = getById(idCliente);
        aplicar(cliente, request);
        return toResponse(clienteRepository.save(Objects.requireNonNull(cliente)));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long idCliente) {
        return toResponse(getById(idCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idCliente) {
        clienteRepository.delete(Objects.requireNonNull(getById(idCliente)));
    }

    private Cliente getById(Long idCliente) {
        return clienteRepository.findById(Objects.requireNonNull(idCliente))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado: " + idCliente));
    }

    private Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        aplicar(cliente, request);
        return cliente;
    }

    private void aplicar(Cliente cliente, ClienteRequest request) {
        cliente.setNombre(request.nombre());
        cliente.setApellido(request.apellido());
        cliente.setEmail(request.email());
        cliente.setTelefono(request.telefono());
        cliente.setDireccion(request.direccion());
        cliente.setCiudad(request.ciudad());
        cliente.setNotas(request.notas());
        if (request.activo() != null) {
            cliente.setActivo(request.activo());
        }
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getIdCliente(), cliente.getNombre(), cliente.getApellido(), cliente.getEmail(),
                cliente.getTelefono(), cliente.getDireccion(), cliente.getCiudad(), cliente.getNotas(), cliente.getActivo()
        );
    }
}
