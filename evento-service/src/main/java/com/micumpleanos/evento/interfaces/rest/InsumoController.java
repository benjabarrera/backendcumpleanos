package com.micumpleanos.evento.interfaces.rest;

import com.micumpleanos.evento.application.dto.InsumoDTO;
import com.micumpleanos.evento.application.dto.MovimientoDTO;
import com.micumpleanos.evento.application.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    public ResponseEntity<InsumoDTO> crearInsumo(@RequestBody InsumoDTO dto) {
        InsumoDTO creado = insumoService.crearInsumo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/ingreso")
    public ResponseEntity<?> registrarIngreso(@RequestBody MovimientoDTO dto) {
        try {
            InsumoDTO actualizado = insumoService.registrarIngreso(dto);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar el ingreso de stock.");
        }
    }

    @GetMapping
    public ResponseEntity<List<InsumoDTO>> listarInsumos() {
        return ResponseEntity.ok(insumoService.listarInsumos());
    }
}
