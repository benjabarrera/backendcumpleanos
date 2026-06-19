package com.micumpleanos.personal.domain.repository;

import com.micumpleanos.personal.domain.entity.Personal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalRepository extends JpaRepository<Personal, Long> {

    Optional<Personal> findByEmail(String email);

    Optional<Personal> findByRut(String rut);

    @EntityGraph(attributePaths = {"rolPersonal"})
    List<Personal> findAllByActivoTrue();
}
