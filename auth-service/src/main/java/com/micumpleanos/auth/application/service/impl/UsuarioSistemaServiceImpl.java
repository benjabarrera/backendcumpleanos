package com.micumpleanos.auth.application.service.impl;

import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaRequest;
import com.micumpleanos.auth.application.dto.usuario.UsuarioSistemaResponse;
import com.micumpleanos.auth.application.service.UsuarioSistemaService;
import com.micumpleanos.auth.domain.entity.UsuarioSistema;
import com.micumpleanos.auth.domain.repository.UsuarioSistemaRepository;
import com.micumpleanos.auth.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioSistemaServiceImpl implements UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioSistemaRepository;

    @Override
    public UsuarioSistemaResponse crear(UsuarioSistemaRequest request) {
        return toResponse(usuarioSistemaRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public UsuarioSistemaResponse actualizar(Long idUsuario, UsuarioSistemaRequest request) {
        UsuarioSistema usuario = getById(idUsuario);
        aplicar(usuario, request);
        return toResponse(usuarioSistemaRepository.save(Objects.requireNonNull(usuario)));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioSistemaResponse buscarPorId(Long idUsuario) {
        return toResponse(getById(idUsuario));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioSistemaResponse buscarPorUsername(String username) {
        return toResponse(usuarioSistemaRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioSistemaResponse> listar() {
        return usuarioSistemaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idUsuario) {
        usuarioSistemaRepository.delete(Objects.requireNonNull(getById(idUsuario)));
    }

    private UsuarioSistema getById(Long idUsuario) {
        return usuarioSistemaRepository.findById(Objects.requireNonNull(idUsuario))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + idUsuario));
    }

    private UsuarioSistema toEntity(UsuarioSistemaRequest request) {
        UsuarioSistema usuario = new UsuarioSistema();
        aplicar(usuario, request);
        return usuario;
    }

    private void aplicar(UsuarioSistema usuario, UsuarioSistemaRequest request) {
        if (request.idPersonal() != null) {
            usuario.setIdPersonal(request.idPersonal());
        } else {
            usuario.setIdPersonal(null);
        }
        usuario.setUsername(request.username());
        usuario.setPasswordHash(request.passwordHash());
        if (request.rolSistema() != null) {
            usuario.setRolSistema(request.rolSistema());
        }
        if (request.activo() != null) {
            usuario.setActivo(request.activo());
        }
        usuario.setUltimoLogin(request.ultimoLogin());
    }

    private UsuarioSistemaResponse toResponse(UsuarioSistema usuario) {
        return new UsuarioSistemaResponse(
                usuario.getIdUsuario(),
                usuario.getIdPersonal() != null ? usuario.getIdPersonal() : null,
                null, // Nombre del personal no disponible sin Feign
                usuario.getUsername(),
                usuario.getRolSistema(),
                usuario.getActivo(),
                usuario.getUltimoLogin()
        );
    }
}
