package com.micumpleanos.evento.application.service;

import com.micumpleanos.evento.application.dto.CotizacionDTO;
import com.micumpleanos.evento.application.service.impl.CotizacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CotizacionServiceTest {

    private CotizacionService cotizacionService;

    @BeforeEach
    void setUp() {
        cotizacionService = new CotizacionServiceImpl();
    }

    @Test
    void calcularCotizacion_HappyPath_ReturnsCorrectCalculation() {
        // Arrange
        Integer cantidadNinos = 10;
        
        // Act
        CotizacionDTO result = cotizacionService.calcularCotizacion(cantidadNinos);
        
        // Assert
        assertNotNull(result);
        assertEquals(10, result.getCantidadNinos());
        assertEquals(30, result.getInsumosNecesarios()); // 10 * 3
        assertEquals(60000.0, result.getCostoInsumos()); // 30 * 2000
        assertEquals(50000.0, result.getCostoBase());
        assertEquals(110000.0, result.getPresupuestoTotal()); // 50000 + 60000
    }

    @Test
    void calcularCotizacion_NegativeQuantity_ThrowsIllegalArgumentException() {
        // Arrange
        Integer cantidadNinos = -5;
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cotizacionService.calcularCotizacion(cantidadNinos);
        });
        
        assertEquals("La cantidad de niños debe ser un número positivo.", exception.getMessage());
    }

    @Test
    void calcularCotizacion_NullQuantity_ThrowsIllegalArgumentException() {
        // Arrange
        Integer cantidadNinos = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cotizacionService.calcularCotizacion(cantidadNinos);
        });

        assertEquals("La cantidad de niños debe ser un número positivo.", exception.getMessage());
    }
}
