package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.evento.MenuEventoResponse;

public interface MenuEventoService {
    MenuEventoResponse buscarPorEvento(Long idEvento);
}
