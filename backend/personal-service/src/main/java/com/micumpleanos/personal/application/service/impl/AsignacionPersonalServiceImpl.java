package com.micumpleanos.personal.application.service.impl;

import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalRequest;
import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalResponse;
import com.micumpleanos.personal.application.service.AsignacionPersonalService;
import com.micumpleanos.personal.domain.entity.AsignacionPersonal;
import com.micumpleanos.personal.domain.entity.Personal;
import com.micumpleanos.personal.domain.enums.EstadoAsignacion;
import com.micumpleanos.personal.domain.repository.AsignacionPersonalRepository;
import com.micumpleanos.personal.domain.repository.PersonalRepository;
import com.micumpleanos.personal.shared.exception.BusinessRuleException;
import com.micumpleanos.personal.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignacionPersonalServiceImpl implements AsignacionPersonalService {

    private final AsignacionPersonalRepository asignacionPersonalRepository;
    private final PersonalRepository personalRepository;

    @Override
    public AsignacionPersonalResponse crear(AsignacionPersonalRequest request) {
        validarDuplicado(request.idEvento(), request.idPersonal());
        validarTopeHorario(request.idPersonal(), request.fechaAsignacion(), request.horaEntrada(), request.horaSalida(), null);
        return toResponse(asignacionPersonalRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public AsignacionPersonalResponse actualizar(Long idAsignacion, AsignacionPersonalRequest request) {
        AsignacionPersonal asignacion = getById(idAsignacion);
        validarDuplicado(request.idEvento(), request.idPersonal(), idAsignacion);
        validarTopeHorario(request.idPersonal(), request.fechaAsignacion(), request.horaEntrada(), request.horaSalida(), idAsignacion);
        aplicar(asignacion, request);
        return toResponse(asignacionPersonalRepository.save(Objects.requireNonNull(asignacion)));
    }

    @Override
    @Transactional(readOnly = true)
    public AsignacionPersonalResponse buscarPorId(Long idAsignacion) {
        return toResponse(getById(idAsignacion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionPersonalResponse> listar() {
        return asignacionPersonalRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionPersonalResponse> listarPorEvento(Long idEvento) {
        return asignacionPersonalRepository.findAllByIdEvento(Objects.requireNonNull(idEvento)).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsignacionPersonalResponse> listarPorEstado(EstadoAsignacion estado) {
        return asignacionPersonalRepository.findAllByEstado(estado).stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idAsignacion) {
        asignacionPersonalRepository.delete(Objects.requireNonNull(getById(idAsignacion)));
    }

    private AsignacionPersonal getById(Long idAsignacion) {
        return asignacionPersonalRepository.findById(Objects.requireNonNull(idAsignacion))
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada: " + idAsignacion));
    }

    private AsignacionPersonal toEntity(AsignacionPersonalRequest request) {
        AsignacionPersonal asignacion = new AsignacionPersonal();
        aplicar(asignacion, request);
        return asignacion;
    }

    private void aplicar(AsignacionPersonal asignacion, AsignacionPersonalRequest request) {
        Personal personal = personalRepository.findById(Objects.requireNonNull(request.idPersonal()))
                .orElseThrow(() -> new ResourceNotFoundException("Personal no encontrado: " + request.idPersonal()));

        asignacion.setIdEvento(request.idEvento());
        asignacion.setPersonal(personal);
        asignacion.setFechaAsignacion(request.fechaAsignacion());
        asignacion.setHoraEntrada(request.horaEntrada());
        asignacion.setHoraSalida(request.horaSalida());
        asignacion.setHoraEntradaReal(request.horaEntradaReal());
        asignacion.setHoraSalidaReal(request.horaSalidaReal());
        if (request.estado() != null) {
            asignacion.setEstado(request.estado());
        }
        asignacion.setNotas(request.notas());
    }

    private void validarDuplicado(Long idEvento, Long idPersonal) {
        validarDuplicado(idEvento, idPersonal, null);
    }

    private void validarDuplicado(Long idEvento, Long idPersonal, Long idAsignacionActual) {
        asignacionPersonalRepository.findByIdEventoAndPersonal_IdPersonal(Objects.requireNonNull(idEvento), Objects.requireNonNull(idPersonal))
                .ifPresent(asignacion -> {
                    if (idAsignacionActual == null || !asignacion.getIdAsignacion().equals(idAsignacionActual)) {
                        throw new BusinessRuleException("Ya existe una asignación para este evento y personal.");
                    }
                });
    }

    private void validarTopeHorario(Long idPersonal, java.time.LocalDate fecha, java.time.LocalTime entrada, java.time.LocalTime salida, Long idAsignacionActual) {
        if (fecha == null || entrada == null || salida == null) return;
        
        List<AsignacionPersonal> asignaciones = asignacionPersonalRepository.findByPersonal_IdPersonalAndFechaAsignacion(idPersonal, fecha);
        for (AsignacionPersonal asignacion : asignaciones) {
            if (idAsignacionActual != null && asignacion.getIdAsignacion().equals(idAsignacionActual)) {
                continue;
            }
            if (asignacion.getHoraEntrada() != null && asignacion.getHoraSalida() != null) {
                // Hay tope si (entrada < salida_existente) y (salida > entrada_existente)
                if (entrada.isBefore(asignacion.getHoraSalida()) && salida.isAfter(asignacion.getHoraEntrada())) {
                    throw new com.micumpleanos.personal.shared.exception.ConflictException("Tope de horario: El personal ya tiene una asignación en esa fecha y rango horario.");
                }
            }
        }
    }

    private AsignacionPersonalResponse toResponse(AsignacionPersonal asignacion) {
        return new AsignacionPersonalResponse(
                asignacion.getIdAsignacion(),
                asignacion.getIdEvento(),
                null, // Nombre del evento no disponible sin Feign
                asignacion.getPersonal().getIdPersonal(),
                asignacion.getPersonal().getNombre() + " " + asignacion.getPersonal().getApellido(),
                asignacion.getPersonal().getRolPersonal().getNombre(),
                asignacion.getFechaAsignacion(),
                asignacion.getHoraEntrada(),
                asignacion.getHoraSalida(),
                asignacion.getHoraEntradaReal(),
                asignacion.getHoraSalidaReal(),
                asignacion.getEstado(),
                asignacion.getNotas()
        );
    }
}
