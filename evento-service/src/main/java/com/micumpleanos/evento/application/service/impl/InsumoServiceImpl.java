package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.InsumoDTO;
import com.micumpleanos.evento.application.dto.MovimientoDTO;
import com.micumpleanos.evento.application.service.InsumoService;
import com.micumpleanos.evento.domain.entity.Insumo;
import com.micumpleanos.evento.domain.entity.MovimientoInventario;
import com.micumpleanos.evento.domain.enums.TipoMovimiento;
import com.micumpleanos.evento.domain.repository.InsumoRepository;
import com.micumpleanos.evento.domain.repository.MovimientoInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;
    private final MovimientoInventarioRepository movimientoRepository;

    @Override
    @Transactional
    public InsumoDTO crearInsumo(InsumoDTO dto) {
        Insumo insumo = Insumo.builder()
                .nombre(dto.getNombre())
                .stockActual(dto.getStockActual() != null ? dto.getStockActual() : 0)
                .stockCriticoUmbral(dto.getStockCriticoUmbral() != null ? dto.getStockCriticoUmbral() : 10)
                .alertaStockCritico(false)
                .build();
        
        // Evaluar alerta inicial
        if (insumo.getStockActual() <= insumo.getStockCriticoUmbral()) {
            insumo.setAlertaStockCritico(true);
        }

        insumo = insumoRepository.save(insumo);
        return mapToDTO(insumo);
    }

    @Override
    @Transactional
    public InsumoDTO registrarIngreso(MovimientoDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor a 0.");
        }

        Insumo insumo = insumoRepository.findById(dto.getIdInsumo())
                .orElseThrow(() -> new IllegalArgumentException("Insumo no encontrado."));

        // Actualizar stock
        insumo.setStockActual(insumo.getStockActual() + dto.getCantidad());

        // Validar alerta
        if (insumo.getAlertaStockCritico() && insumo.getStockActual() > insumo.getStockCriticoUmbral()) {
            insumo.setAlertaStockCritico(false);
        }

        insumo = insumoRepository.save(insumo);

        // Registrar log de movimiento
        MovimientoInventario movimiento = MovimientoInventario.builder()
                .insumo(insumo)
                .tipoMovimiento(TipoMovimiento.INGRESO)
                .cantidad(dto.getCantidad())
                .fechaMovimiento(LocalDateTime.now())
                .observacion(dto.getObservacion())
                .build();
        
        movimientoRepository.save(movimiento);

        return mapToDTO(insumo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoDTO> listarInsumos() {
        return insumoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private InsumoDTO mapToDTO(Insumo insumo) {
        return InsumoDTO.builder()
                .idInsumo(insumo.getIdInsumo())
                .nombre(insumo.getNombre())
                .stockActual(insumo.getStockActual())
                .stockCriticoUmbral(insumo.getStockCriticoUmbral())
                .alertaStockCritico(insumo.getAlertaStockCritico())
                .build();
    }
}
