package com.micumpleanos.personal.domain.repository;

import com.micumpleanos.personal.domain.entity.RolPersonal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolPersonalRepository extends JpaRepository<RolPersonal, Long> {
    Optional<RolPersonal> findByNombre(String nombre);
}
