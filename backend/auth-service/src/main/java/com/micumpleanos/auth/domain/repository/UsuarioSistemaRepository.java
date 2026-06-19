package com.micumpleanos.auth.domain.repository;

import com.micumpleanos.auth.domain.entity.UsuarioSistema;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {


    Optional<UsuarioSistema> findByUsername(String username);
}
