package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.InsumoDTO;
import com.micumpleanos.evento.application.dto.MovimientoDTO;
import com.micumpleanos.evento.application.service.impl.InsumoServiceImpl;
import com.micumpleanos.evento.domain.entity.Insumo;
import com.micumpleanos.evento.domain.repository.InsumoRepository;
import com.micumpleanos.evento.domain.repository.MovimientoInventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InsumoServiceTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private MovimientoInventarioRepository movimientoRepository;

    @InjectMocks
    private InsumoServiceImpl insumoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarIngreso_ValidQuantity_UpdatesStockAndDisablesAlert() {
        // Arrange
        Insumo insumoMock = Insumo.builder()
                .idInsumo(1L)
                .nombre("Globos")
                .stockActual(5)
                .stockCriticoUmbral(10)
                .alertaStockCritico(true)
                .build();

        MovimientoDTO dto = MovimientoDTO.builder()
                .idInsumo(1L)
                .cantidad(20) // El stock será 25, > 10, por lo que la alerta debe desactivarse
                .observacion("Ingreso proveedor")
                .build();

        when(insumoRepository.findById(1L)).thenReturn(Optional.of(insumoMock));
        when(insumoRepository.save(any(Insumo.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        InsumoDTO result = insumoService.registrarIngreso(dto);

        // Assert
        assertNotNull(result);
        assertEquals(25, result.getStockActual());
        assertFalse(result.getAlertaStockCritico());
        
        verify(insumoRepository, times(1)).save(any(Insumo.class));
        verify(movimientoRepository, times(1)).save(any());
    }

    @Test
    void registrarIngreso_NegativeOrZeroQuantity_ThrowsException() {
        // Arrange
        MovimientoDTO dto = MovimientoDTO.builder()
                .idInsumo(1L)
                .cantidad(0)
                .build();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.registrarIngreso(dto);
        });

        assertEquals("La cantidad a ingresar debe ser mayor a 0.", exception.getMessage());
        verify(insumoRepository, never()).save(any());
        verify(movimientoRepository, never()).save(any());
    }

    @Test
    void registrarIngreso_InsumoNotFound_ThrowsException() {
        // Arrange
        MovimientoDTO dto = MovimientoDTO.builder()
                .idInsumo(99L)
                .cantidad(10)
                .build();

        when(insumoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            insumoService.registrarIngreso(dto);
        });

        assertEquals("Insumo no encontrado.", exception.getMessage());
    }
}
