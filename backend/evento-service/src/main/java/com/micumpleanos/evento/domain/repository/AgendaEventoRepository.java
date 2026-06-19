package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.AgendaEvento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendaEventoRepository extends JpaRepository<AgendaEvento, Long> {

    @EntityGraph(attributePaths = {"evento"})
    Optional<AgendaEvento> findByEvento_IdEvento(Long idEvento);
}
