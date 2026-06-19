package com.micumpleanos.auth.interfaces.rest;

import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaRequest;
import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaResponse;
import com.micumpleanos.auth.application.service.UsuarioSistemaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios-sistema")
@RequiredArgsConstructor
public class UsuarioSistemaController {

    private final UsuarioSistemaService usuarioSistemaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioSistemaResponse crear(@Valid @RequestBody UsuarioSistemaRequest request) {
        return usuarioSistemaService.crear(request);
    }

    @PutMapping("/{idUsuario}")
    public UsuarioSistemaResponse actualizar(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioSistemaRequest request) {
        return usuarioSistemaService.actualizar(idUsuario, request);
    }

    @GetMapping("/{idUsuario}")
    public UsuarioSistemaResponse buscarPorId(@PathVariable Long idUsuario) {
        return usuarioSistemaService.buscarPorId(idUsuario);
    }

    @GetMapping
    public List<UsuarioSistemaResponse> listar(@RequestParam(required = false) String username) {
        return username != null ? List.of(usuarioSistemaService.buscarPorUsername(username)) : usuarioSistemaService.listar();
    }

    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idUsuario) {
        usuarioSistemaService.eliminar(idUsuario);
    }
}
