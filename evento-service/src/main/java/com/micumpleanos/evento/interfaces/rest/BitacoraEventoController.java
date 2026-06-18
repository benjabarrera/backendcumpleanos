package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoRequest;
import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoResponse;
import com.micumpleanos.evento.application.service.BitacoraEventoService;
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
@RequestMapping("/api/v1/bitacoras-evento")
@RequiredArgsConstructor
public class BitacoraEventoController {

    private final BitacoraEventoService bitacoraEventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BitacoraEventoResponse crear(@Valid @RequestBody BitacoraEventoRequest request) {
        return bitacoraEventoService.crear(request);
    }

    @PutMapping("/{idBitacora}")
    public BitacoraEventoResponse actualizar(@PathVariable Long idBitacora, @Valid @RequestBody BitacoraEventoRequest request) {
        return bitacoraEventoService.actualizar(idBitacora, request);
    }

    @GetMapping("/{idBitacora}")
    public BitacoraEventoResponse buscarPorId(@PathVariable Long idBitacora) {
        return bitacoraEventoService.buscarPorId(idBitacora);
    }

    @GetMapping
    public List<BitacoraEventoResponse> listar(@RequestParam(required = false) Long idEvento) {
        return idEvento != null ? bitacoraEventoService.listarPorEvento(idEvento) : bitacoraEventoService.listar();
    }

    @DeleteMapping("/{idBitacora}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idBitacora) {
        bitacoraEventoService.eliminar(idBitacora);
    }
}
