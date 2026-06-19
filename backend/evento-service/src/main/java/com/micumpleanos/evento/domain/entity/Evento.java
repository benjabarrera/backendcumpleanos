package com.micumpleanos.evento.domain.entity;

import com.micumpleanos.evento.domain.enums.EstadoEvento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "evento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_fiesta", nullable = false)
    private TipoFiesta tipoFiesta;

    @Column(name = "nombre_celebrado", nullable = false, length = 100)
    private String nombreCelebrado;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false, length = 255)
    private String lugar;

    @Column(name = "cantidad_ninos", nullable = false)
    private Integer cantidadNinos;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEvento estado = EstadoEvento.SOLICITADO;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @OneToOne(mappedBy = "evento", fetch = FetchType.LAZY)
    private MenuEvento menuEvento;
}
