package com.micumpleanos.evento.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "insumo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insumo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer stockActual;

    @Column(nullable = false)
    private Integer stockCriticoUmbral;

    @Builder.Default
    @Column(nullable = false)
    private Boolean alertaStockCritico = false;
}
