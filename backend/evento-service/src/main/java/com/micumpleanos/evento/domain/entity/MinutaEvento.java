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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "minuta_evento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinutaEvento extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_minuta")
    private Long idMinuta;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento", nullable = false, unique = true)
    private Evento evento;

    @Column(name = "numero_minuta", nullable = false, unique = true, length = 20)
    private String numeroMinuta;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "contenido_html", columnDefinition = "LONGTEXT")
    private String contenidoHtml;

    @Builder.Default
    @Column(name = "enviada_cliente", nullable = false)
    private Boolean enviadaCliente = Boolean.FALSE;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Builder.Default
    @Column(name = "firma_cliente", nullable = false)
    private Boolean firmaCliente = Boolean.FALSE;
}
