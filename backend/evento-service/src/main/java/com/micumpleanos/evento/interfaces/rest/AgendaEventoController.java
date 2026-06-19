package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.agenda.AgendaEventoRequest;
import com.micumpleanos.evento.application.dto.agenda.AgendaEventoResponse;
import com.micumpleanos.evento.application.service.AgendaEventoService;
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
@RequestMapping("/api/v1/agendas-evento")
@RequiredArgsConstructor
public class AgendaEventoController {

    private final AgendaEventoService agendaEventoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaEventoResponse crear(@Valid @RequestBody AgendaEventoRequest request) {
        return agendaEventoService.crear(request);
    }

    @PutMapping("/{idAgenda}")
    public AgendaEventoResponse actualizar(@PathVariable Long idAgenda, @Valid @RequestBody AgendaEventoRequest request) {
        return agendaEventoService.actualizar(idAgenda, request);
    }

    @GetMapping("/{idAgenda}")
    public AgendaEventoResponse buscarPorId(@PathVariable Long idAgenda) {
        return agendaEventoService.buscarPorId(idAgenda);
    }

    @GetMapping("/evento/{idEvento}")
    public AgendaEventoResponse buscarPorEvento(@PathVariable Long idEvento) {
        return agendaEventoService.buscarPorEvento(idEvento);
    }

    @GetMapping
    public List<AgendaEventoResponse> listar() {
        return agendaEventoService.listar();
    }

    @DeleteMapping("/{idAgenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idAgenda) {
        agendaEventoService.eliminar(idAgenda);
    }
}
