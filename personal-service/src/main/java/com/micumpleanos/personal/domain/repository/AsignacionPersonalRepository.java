package com.micumpleanos.personal.domain.repository;

import com.micumpleanos.personal.domain.entity.AsignacionPersonal;
import com.micumpleanos.personal.domain.enums.EstadoAsignacion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignacionPersonalRepository extends JpaRepository<AsignacionPersonal, Long> {

    Optional<AsignacionPersonal> findByIdEventoAndPersonal_IdPersonal(Long idEvento, Long idPersonal);

    @EntityGraph(attributePaths = {"personal", "personal.rolPersonal"})
    List<AsignacionPersonal> findAllByIdEvento(Long idEvento);

    @EntityGraph(attributePaths = {"personal", "personal.rolPersonal"})
    List<AsignacionPersonal> findAllByEstado(EstadoAsignacion estado);
}
