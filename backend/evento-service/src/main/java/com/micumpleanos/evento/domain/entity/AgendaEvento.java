package com.micumpleanos.evento.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.time.LocalTime;

@Entity
@Table(name = "agenda_evento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgendaEvento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Long idAgenda;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento", nullable = false, unique = true)
    private Evento evento;

    @Column(name = "hora_montaje", nullable = false)
    private LocalTime horaMontaje;

    @Column(name = "hora_bienvenida", nullable = false)
    private LocalTime horaBienvenida;

    @Column(name = "hora_juegos", nullable = false)
    private LocalTime horaJuegos;

    @Column(name = "hora_torta", nullable = false)
    private LocalTime horaTorta;

    @Column(name = "hora_desmontaje", nullable = false)
    private LocalTime horaDesmontaje;

    @Column(columnDefinition = "TEXT")
    private String detalles;
}
