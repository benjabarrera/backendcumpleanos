package com.micumpleanos.evento.domain.entity;

import com.micumpleanos.evento.domain.enums.TipoPinata;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menu_evento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEvento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long idMenu;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento", nullable = false, unique = true)
    private Evento evento;

    @Column(name = "platos_dulces", nullable = false)
    private Integer platosDulces;

    @Column(name = "platos_salados", nullable = false)
    private Integer platosSalados;

    @Column(nullable = false)
    private Integer tortas;

    @Column(name = "bolsas_sorpresa", nullable = false)
    private Integer bolsasSorpresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pinata", nullable = false, length = 20)
    private TipoPinata tipoPinata;

    @Builder.Default
    @Column(name = "cantidad_pinatas", nullable = false)
    private Integer cantidadPinatas = 1;

    @Column(name = "notas_menu", columnDefinition = "TEXT")
    private String notasMenu;
}
