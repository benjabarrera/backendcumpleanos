package com.micumpleanos.personal.application.service.impl;

import com.micumpleanos.personal.application.dto.personal.PersonalRequest;
import com.micumpleanos.personal.application.dto.personal.PersonalResponse;
import com.micumpleanos.personal.application.service.PersonalService;
import com.micumpleanos.personal.domain.entity.Personal;
import com.micumpleanos.personal.domain.entity.RolPersonal;
import com.micumpleanos.personal.domain.repository.PersonalRepository;
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
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;
    private final RolPersonalRepository rolPersonalRepository;

    @Override
    public PersonalResponse crear(PersonalRequest request) {
        return toResponse(personalRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public PersonalResponse actualizar(Long idPersonal, PersonalRequest request) {
        Personal personal = getById(idPersonal);
        aplicar(personal, request);
        return toResponse(personalRepository.save(Objects.requireNonNull(personal)));
    }

    @Override
    @Transactional(readOnly = true)
    public PersonalResponse buscarPorId(Long idPersonal) {
        return toResponse(getById(idPersonal));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalResponse> listar() {
        return personalRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalResponse> listarActivos() {
        return personalRepository.findAllByActivoTrue().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idPersonal) {
        personalRepository.delete(Objects.requireNonNull(getById(idPersonal)));
    }

    private Personal getById(Long idPersonal) {
        return personalRepository.findById(Objects.requireNonNull(idPersonal))
                .orElseThrow(() -> new ResourceNotFoundException("Personal no encontrado: " + idPersonal));
    }

    private Personal toEntity(PersonalRequest request) {
        Personal personal = new Personal();
        aplicar(personal, request);
        return personal;
    }

    private void aplicar(Personal personal, PersonalRequest request) {
        RolPersonal rol = rolPersonalRepository.findById(Objects.requireNonNull(request.idRol()))
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + request.idRol()));
        personal.setRolPersonal(rol);
        personal.setNombre(request.nombre());
        personal.setApellido(request.apellido());
        personal.setEmail(request.email());
        personal.setTelefono(request.telefono());
        personal.setRut(request.rut());
        if (request.activo() != null) {
            personal.setActivo(request.activo());
        }
    }

    private PersonalResponse toResponse(Personal personal) {
        return new PersonalResponse(
                personal.getIdPersonal(),
                personal.getRolPersonal().getIdRol(),
                personal.getRolPersonal().getNombre(),
                personal.getNombre(),
                personal.getApellido(),
                personal.getEmail(),
                personal.getTelefono(),
                personal.getRut(),
                personal.getActivo()
        );
    }
}
