package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.evento.EventoCreateRequest;
import com.micumpleanos.evento.application.dto.evento.EventoResponse;
import com.micumpleanos.evento.application.dto.evento.EventoUpdateRequest;
import com.micumpleanos.evento.application.service.EventoService;
import com.micumpleanos.evento.domain.enums.EstadoEvento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoResponse crear(@Valid @RequestBody EventoCreateRequest request) {
        return eventoService.crearEvento(request);
    }

    @PutMapping("/{idEvento}")
    public EventoResponse actualizar(@PathVariable long idEvento, @Valid @RequestBody EventoUpdateRequest request) {
        return eventoService.actualizarEvento(idEvento, request);
    }

    @PostMapping("/{idEvento}/confirmar")
    public EventoResponse confirmar(@PathVariable long idEvento) {
        return eventoService.confirmarEvento(idEvento);
    }

    @GetMapping("/{idEvento}")
    public EventoResponse obtenerPorId(@PathVariable long idEvento) {
        return eventoService.buscarPorId(idEvento);
    }

    @GetMapping
    public List<EventoResponse> listar(
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        if (estado != null) {
            return eventoService.buscarPorEstado(estado);
        }
        if (fecha != null) {
            return eventoService.buscarPorFecha(fecha);
        }
        return eventoService.listarTodos();
    }

    @DeleteMapping("/{idEvento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable long idEvento) {
        eventoService.eliminarEvento(idEvento);
    }
}
