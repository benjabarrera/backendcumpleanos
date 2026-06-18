package com.micumpleanos.personal.application.service.impl;

import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalRequest;
import com.micumpleanos.personal.application.dto.rolpersonal.RolPersonalResponse;
import com.micumpleanos.personal.application.service.RolPersonalService;
import com.micumpleanos.personal.domain.entity.RolPersonal;
import com.micumpleanos.personal.domain.repository.RolPersonalRepository;
import com.micumpleanos.personal.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class RolPersonalServiceImpl implements RolPersonalService {

    private final RolPersonalRepository rolPersonalRepository;

    @Override
    public RolPersonalResponse crear(RolPersonalRequest request) {
        return toResponse(rolPersonalRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public RolPersonalResponse actualizar(Long idRol, RolPersonalRequest request) {
        RolPersonal rol = getById(idRol);
        rol.setNombre(request.nombre());
        rol.setDescripcion(request.descripcion());
        rol.setTarifaHora(request.tarifaHora());
        return toResponse(rolPersonalRepository.save(Objects.requireNonNull(rol)));
    }

    @Override
    @Transactional(readOnly = true)
    public RolPersonalResponse buscarPorId(Long idRol) {
        return toResponse(getById(idRol));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolPersonalResponse> listar() {
        return rolPersonalRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idRol) {
        rolPersonalRepository.delete(Objects.requireNonNull(getById(idRol)));
    }

    private RolPersonal getById(Long idRol) {
        return rolPersonalRepository.findById(Objects.requireNonNull(idRol))
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + idRol));
    }

    private RolPersonal toEntity(RolPersonalRequest request) {
        return RolPersonal.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .tarifaHora(request.tarifaHora())
                .build();
    }

    private RolPersonalResponse toResponse(RolPersonal rol) {
        return new RolPersonalResponse(rol.getIdRol(), rol.getNombre(), rol.getDescripcion(), rol.getTarifaHora());
    }
}
