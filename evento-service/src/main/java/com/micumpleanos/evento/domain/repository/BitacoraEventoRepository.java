package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.BitacoraEvento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitacoraEventoRepository extends JpaRepository<BitacoraEvento, Long> {

    @EntityGraph(attributePaths = {"evento"})
    List<BitacoraEvento> findAllByEvento_IdEventoOrderByTimestampLogDesc(Long idEvento);
}
