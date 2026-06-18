package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.evento.MenuEventoResponse;
import com.micumpleanos.evento.application.service.MenuEventoService;
import com.micumpleanos.evento.domain.entity.MenuEvento;
import com.micumpleanos.evento.domain.repository.MenuEventoRepository;
import com.micumpleanos.evento.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuEventoServiceImpl implements MenuEventoService {

    private final MenuEventoRepository menuEventoRepository;

    @Override
    public MenuEventoResponse buscarPorEvento(Long idEvento) {
        MenuEvento menu = menuEventoRepository.findByEvento_IdEvento(Objects.requireNonNull(idEvento))
                .orElseThrow(() -> new ResourceNotFoundException("Menú no encontrado para evento: " + idEvento));

        return new MenuEventoResponse(
                menu.getIdMenu(),
                menu.getPlatosDulces(),
                menu.getPlatosSalados(),
                menu.getTortas(),
                menu.getBolsasSorpresa(),
                menu.getTipoPinata(),
                menu.getCantidadPinatas(),
                menu.getNotasMenu()
        );
    }
}
