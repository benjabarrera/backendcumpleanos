package com.micumpleanos.cliente.interfaces.rest;

import com.micumpleanos.cliente.application.dto.cliente.ClienteRequest;
import com.micumpleanos.cliente.application.dto.cliente.ClienteResponse;
import com.micumpleanos.cliente.application.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse crear(@Valid @RequestBody ClienteRequest request) {
        return clienteService.crear(request);
    }

    @PutMapping("/{idCliente}")
    public ClienteResponse actualizar(@PathVariable Long idCliente, @Valid @RequestBody ClienteRequest request) {
        return clienteService.actualizar(idCliente, request);
    }

    @GetMapping("/{idCliente}")
    public ClienteResponse buscarPorId(@PathVariable Long idCliente) {
        return clienteService.buscarPorId(idCliente);
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @DeleteMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idCliente) {
        clienteService.eliminar(idCliente);
    }
}
