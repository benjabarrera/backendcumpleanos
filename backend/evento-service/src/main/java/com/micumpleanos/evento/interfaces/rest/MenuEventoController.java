package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.evento.MenuEventoResponse;
import com.micumpleanos.evento.application.service.MenuEventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuEventoController {

    private final MenuEventoService menuEventoService;

    @GetMapping("/evento/{idEvento}")
    public MenuEventoResponse buscarPorEvento(@PathVariable Long idEvento) {
        return menuEventoService.buscarPorEvento(idEvento);
    }
}
