package com.micumpleanos.personal.interfaces.rest;

import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalRequest;
import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalResponse;
import com.micumpleanos.personal.application.service.RolPersonalService;
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
@RequestMapping("/api/v1/roles-personal")
@RequiredArgsConstructor
public class RolPersonalController {

    private final RolPersonalService rolPersonalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolPersonalResponse crear(@Valid @RequestBody RolPersonalRequest request) {
        return rolPersonalService.crear(request);
    }

    @PutMapping("/{idRol}")
    public RolPersonalResponse actualizar(@PathVariable Long idRol, @Valid @RequestBody RolPersonalRequest request) {
        return rolPersonalService.actualizar(idRol, request);
    }

    @GetMapping("/{idRol}")
    public RolPersonalResponse buscarPorId(@PathVariable Long idRol) {
        return rolPersonalService.buscarPorId(idRol);
    }

    @GetMapping
    public List<RolPersonalResponse> listar() {
        return rolPersonalService.listar();
    }

    @DeleteMapping("/{idRol}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idRol) {
        rolPersonalService.eliminar(idRol);
    }
}
