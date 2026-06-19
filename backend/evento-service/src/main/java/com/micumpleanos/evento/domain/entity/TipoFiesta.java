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
@Table(name = "tipo_fiesta")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoFiesta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_fiesta")
    private Long idTipoFiesta;

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    @Column(name = "color_hex", length = 7)
    private String colorHex = "#FF6B9D";

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;
}
