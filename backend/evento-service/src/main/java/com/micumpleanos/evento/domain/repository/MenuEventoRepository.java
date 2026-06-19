package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.MenuEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuEventoRepository extends JpaRepository<MenuEvento, Long> {
    Optional<MenuEvento> findByEvento_IdEvento(Long idEvento);
}
