package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.minuta.MinutaEventoRequest;
import com.micumpleanos.evento.application.dto.minuta.MinutaEventoResponse;
import com.micumpleanos.evento.application.service.MinutaEventoService;
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
@RequestMapping("/api/v1/minutas-evento")
@RequiredArgsConstructor
public class MinutaEventoController {

    private final MinutaEventoService minutaEventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MinutaEventoResponse crear(@Valid @RequestBody MinutaEventoRequest request) {
        return minutaEventoService.crear(request);
    }

    @PutMapping("/{idMinuta}")
    public MinutaEventoResponse actualizar(@PathVariable Long idMinuta, @Valid @RequestBody MinutaEventoRequest request) {
        return minutaEventoService.actualizar(idMinuta, request);
    }

    @GetMapping("/{idMinuta}")
    public MinutaEventoResponse buscarPorId(@PathVariable Long idMinuta) {
        return minutaEventoService.buscarPorId(idMinuta);
    }

    @GetMapping("/evento/{idEvento}")
    public MinutaEventoResponse buscarPorEvento(@PathVariable Long idEvento) {
        return minutaEventoService.buscarPorEvento(idEvento);
    }

    @GetMapping
    public List<MinutaEventoResponse> listar() {
        return minutaEventoService.listar();
    }

    @DeleteMapping("/{idMinuta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idMinuta) {
        minutaEventoService.eliminar(idMinuta);
    }
}
