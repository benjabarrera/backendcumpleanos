package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.TipoFiesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoFiestaRepository extends JpaRepository<TipoFiesta, Long> {
    Optional<TipoFiesta> findByNombre(String nombre);
}
