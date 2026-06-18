package com.micumpleanos.personal.application.service;

import com.micumpleanos.personal.application.dto.asignacion.AsignacionPersonalRequest;
import com.micumpleanos.personal.application.service.impl.AsignacionPersonalServiceImpl;
import com.micumpleanos.personal.domain.entity.AsignacionPersonal;
import com.micumpleanos.personal.domain.entity.Personal;
import com.micumpleanos.personal.domain.entity.RolPersonal;
import com.micumpleanos.personal.domain.enums.EstadoAsignacion;
import com.micumpleanos.personal.domain.repository.AsignacionPersonalRepository;
import com.micumpleanos.personal.domain.repository.PersonalRepository;
import com.micumpleanos.personal.shared.exception.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

class AsignacionPersonalServiceTest {

    @Mock
    private AsignacionPersonalRepository asignacionRepository;

    @Mock
    private PersonalRepository personalRepository;

    @InjectMocks
    private AsignacionPersonalServiceImpl asignacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearAsignacion_SinTopeHorario_Success() {
        // Arrange
        Long idPersonal = 1L;
        LocalDate fecha = LocalDate.of(2025, 10, 10);
        LocalTime entrada = LocalTime.of(14, 0);
        LocalTime salida = LocalTime.of(18, 0);

        AsignacionPersonalRequest request = new AsignacionPersonalRequest(
                100L, idPersonal, fecha, entrada, salida, null, null, EstadoAsignacion.ASIGNADO, "Ok"
        );

        Personal personal = new Personal();
        personal.setIdPersonal(idPersonal);
        personal.setNombre("Juan");
        personal.setApellido("Perez");
        
        RolPersonal rol = new RolPersonal();
        rol.setNombre("Animador");
        personal.setRolPersonal(rol);

        when(personalRepository.findById(idPersonal)).thenReturn(Optional.of(personal));
        when(asignacionRepository.findByIdEventoAndPersonal_IdPersonal(100L, idPersonal)).thenReturn(Optional.empty());
        when(asignacionRepository.findByPersonal_IdPersonalAndFechaAsignacion(idPersonal, fecha)).thenReturn(Collections.emptyList());
        when(asignacionRepository.save(any(AsignacionPersonal.class))).thenAnswer(i -> {
            AsignacionPersonal a = i.getArgument(0);
            a.setIdAsignacion(1L);
            return a;
        });

        // Act
        var response = asignacionService.crear(request);

        // Assert
        assertEquals(1L, response.idAsignacion());
        verify(asignacionRepository).save(any(AsignacionPersonal.class));
    }

    @Test
    void crearAsignacion_ConTopeHorario_ThrowsConflictException() {
        // Arrange
        Long idPersonal = 1L;
        LocalDate fecha = LocalDate.of(2025, 10, 10);
        
        // Asignación existente de 12:00 a 16:00
        AsignacionPersonal asignacionExistente = new AsignacionPersonal();
        asignacionExistente.setIdAsignacion(10L);
        asignacionExistente.setHoraEntrada(LocalTime.of(12, 0));
        asignacionExistente.setHoraSalida(LocalTime.of(16, 0));

        // Nueva solicitud de 15:00 a 19:00 (Cruza con la existente)
        LocalTime entrada = LocalTime.of(15, 0);
        LocalTime salida = LocalTime.of(19, 0);

        AsignacionPersonalRequest request = new AsignacionPersonalRequest(
                100L, idPersonal, fecha, entrada, salida, null, null, EstadoAsignacion.ASIGNADO, "Cruce"
        );

        when(asignacionRepository.findByIdEventoAndPersonal_IdPersonal(100L, idPersonal)).thenReturn(Optional.empty());
        when(asignacionRepository.findByPersonal_IdPersonalAndFechaAsignacion(idPersonal, fecha))
                .thenReturn(List.of(asignacionExistente));

        // Act & Assert
        Exception exception = assertThrows(ConflictException.class, () -> {
            asignacionService.crear(request);
        });

        assertEquals("Tope de horario: El personal ya tiene una asignación en esa fecha y rango horario.", exception.getMessage());
        verify(asignacionRepository, never()).save(any());
    }
}
