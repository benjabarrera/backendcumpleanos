package com.micumpleanos.personal.interfaces.rest;

import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalRequest;
import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalResponse;
import com.micumpleanos.personal.application.service.AsignacionPersonalService;
import com.micumpleanos.personal.domain.enums.EstadoAsignacion;
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
@RequestMapping("/api/v1/asignaciones-personal")
@RequiredArgsConstructor
public class AsignacionPersonalController {

    private final AsignacionPersonalService asignacionPersonalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsignacionPersonalResponse crear(@Valid @RequestBody AsignacionPersonalRequest request) {
        return asignacionPersonalService.crear(request);
    }

    @PutMapping("/{idAsignacion}")
    public AsignacionPersonalResponse actualizar(@PathVariable Long idAsignacion, @Valid @RequestBody AsignacionPersonalRequest request) {
        return asignacionPersonalService.actualizar(idAsignacion, request);
    }

    @GetMapping("/{idAsignacion}")
    public AsignacionPersonalResponse buscarPorId(@PathVariable Long idAsignacion) {
        return asignacionPersonalService.buscarPorId(idAsignacion);
    }

    @GetMapping
    public List<AsignacionPersonalResponse> listar(
            @RequestParam(required = false) Long idEvento,
            @RequestParam(required = false) EstadoAsignacion estado
    ) {
        if (idEvento != null) {
            return asignacionPersonalService.listarPorEvento(idEvento);
        }
        if (estado != null) {
            return asignacionPersonalService.listarPorEstado(estado);
        }
        return asignacionPersonalService.listar();
    }

    @DeleteMapping("/{idAsignacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idAsignacion) {
        asignacionPersonalService.eliminar(idAsignacion);
    }
}
