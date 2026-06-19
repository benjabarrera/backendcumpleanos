package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.MinutaEvento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MinutaEventoRepository extends JpaRepository<MinutaEvento, Long> {

    @EntityGraph(attributePaths = {"evento"})
    Optional<MinutaEvento> findByEvento_IdEvento(Long idEvento);

    Optional<MinutaEvento> findByNumeroMinuta(String numeroMinuta);
}
