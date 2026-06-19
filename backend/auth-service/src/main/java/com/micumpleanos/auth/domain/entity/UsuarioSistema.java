package com.micumpleanos.auth.domain.entity;

import com.micumpleanos.auth.domain.enums.RolSistema;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_sistema")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistema extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_personal")
    private Long idPersonal;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "rol_sistema", nullable = false, length = 20)
    private RolSistema rolSistema = RolSistema.STAFF;

    @Builder.Default
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
}
