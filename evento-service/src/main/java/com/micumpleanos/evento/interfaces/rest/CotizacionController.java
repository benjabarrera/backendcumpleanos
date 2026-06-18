package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.CotizacionDTO;
import com.micumpleanos.evento.application.service.CotizacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos/cotizacion")
@RequiredArgsConstructor
public class CotizacionController {

    private final CotizacionService cotizacionService;

    @GetMapping
    public ResponseEntity<?> solicitarCotizacion(@RequestParam(name = "cantidad_ninos") Integer cantidadNinos) {
        try {
            CotizacionDTO cotizacion = cotizacionService.calcularCotizacion(cantidadNinos);
            return ResponseEntity.ok(cotizacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno al calcular cotización.");
        }
    }
}
