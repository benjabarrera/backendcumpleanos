package com.micumpleanos.personal.interfaces.rest;

import com.micumpleanos.personal.application.dto.personal.PersonalRequest;
import com.micumpleanos.personal.application.dto.personal.PersonalResponse;
import com.micumpleanos.personal.application.service.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalResponse crear(@Valid @RequestBody PersonalRequest request) {
        return personalService.crear(request);
    }

    @PutMapping("/{idPersonal}")
    public PersonalResponse actualizar(@PathVariable Long idPersonal, @Valid @RequestBody PersonalRequest request) {
        return personalService.actualizar(idPersonal, request);
    }

    @GetMapping("/{idPersonal}")
    public PersonalResponse buscarPorId(@PathVariable Long idPersonal) {
        return personalService.buscarPorId(idPersonal);
    }

    @GetMapping
    public List<PersonalResponse> listar() {
        return personalService.listar();
    }

    @GetMapping("/activos")
    public List<PersonalResponse> listarActivos() {
        return personalService.listarActivos();
    }

    @DeleteMapping("/{idPersonal}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long idPersonal) {
        personalService.eliminar(idPersonal);
    }
}
