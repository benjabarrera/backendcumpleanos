package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaRequest;
import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaResponse;
import com.micumpleanos.evento.application.service.TipoFiestaService;
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
@RequestMapping("/api/v1/tipos-fiesta")
@RequiredArgsConstructor
public class TipoFiestaController {

    private final TipoFiestaService tipoFiestaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoFiestaResponse crear(@Valid @RequestBody TipoFiestaRequest request) {
        return tipoFiestaService.crear(request);
    }

    @PutMapping("/{idTipoFiesta}")
    public TipoFiestaResponse actualizar(@PathVariable Long idTipoFiesta, @Valid @RequestBody TipoFiestaRequest request) {
        return tipoFiestaService.actualizar(idTipoFiesta, request);
    }

    @GetMapping("/{idTipoFiesta}")
    public TipoFiestaResponse buscarPorId(@PathVariable Long idTipoFiesta) {
        return tipoFiestaService.buscarPorId(idTipoFiesta);
    }

    @GetMapping
    public List<TipoFiestaResponse> listar() {
        return tipoFiestaService.listar();
    }

    @DeleteMapping("/{idTipoFiesta}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idTipoFiesta) {
        tipoFiestaService.eliminar(idTipoFiesta);
    }
}
