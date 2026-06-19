package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.Evento;
import com.micumpleanos.evento.domain.enums.EstadoEvento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @EntityGraph(attributePaths = {"tipoFiesta", "menuEvento"})
    Optional<Evento> findByIdEvento(Long idEvento);

    @EntityGraph(attributePaths = {"tipoFiesta", "menuEvento"})
    List<Evento> findAllByEstado(EstadoEvento estado);

    @EntityGraph(attributePaths = {"tipoFiesta", "menuEvento"})
    List<Evento> findAllByFechaEvento(LocalDate fechaEvento);

    @EntityGraph(attributePaths = {"tipoFiesta", "menuEvento"})
    List<Evento> findAllByFechaEventoBetween(LocalDate desde, LocalDate hasta);
}
