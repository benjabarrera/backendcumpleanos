package com.micumpleanos.personal.application.service;

import com.micumpleanos.personal.application.dto.personal.PersonalRequest;
import com.micumpleanos.personal.application.dto.personal.PersonalResponse;

import java.util.List;

public interface PersonalService {
    PersonalResponse crear(PersonalRequest request);
    PersonalResponse actualizar(Long idPersonal, PersonalRequest request);
    PersonalResponse buscarPorId(Long idPersonal);
    List<PersonalResponse> listar();
    List<PersonalResponse> listarActivos();
    void eliminar(Long idPersonal);
}
