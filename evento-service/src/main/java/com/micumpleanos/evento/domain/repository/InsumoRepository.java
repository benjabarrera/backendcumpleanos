package com.micumpleanos.evento.domain.repository;

import com.micumpleanos.evento.domain.entity.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
