package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoRequest;
import com.micumpleanos.evento.application.dto.bitacora.BitacoraEventoResponse;
import com.micumpleanos.evento.application.service.BitacoraEventoService;
import com.micumpleanos.evento.domain.entity.BitacoraEvento;
import com.micumpleanos.evento.domain.entity.Evento;
import com.micumpleanos.evento.domain.enums.TipoRegistroBitacora;
import com.micumpleanos.evento.domain.repository.BitacoraEventoRepository;
import com.micumpleanos.evento.domain.repository.EventoRepository;
import com.micumpleanos.evento.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class BitacoraEventoServiceImpl implements BitacoraEventoService {

    private final BitacoraEventoRepository bitacoraEventoRepository;
    private final EventoRepository eventoRepository;

    @Override
    public BitacoraEventoResponse crear(BitacoraEventoRequest request) {
        return toResponse(bitacoraEventoRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public BitacoraEventoResponse actualizar(Long idBitacora, BitacoraEventoRequest request) {
        BitacoraEvento bitacora = getById(idBitacora);
        aplicar(bitacora, request);
        return toResponse(bitacoraEventoRepository.save(Objects.requireNonNull(bitacora)));
    }

    @Override
    @Transactional(readOnly = true)
    public BitacoraEventoResponse buscarPorId(Long idBitacora) {
        return toResponse(getById(idBitacora));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraEventoResponse> listar() {
        return bitacoraEventoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraEventoResponse> listarPorEvento(Long idEvento) {
        return bitacoraEventoRepository.findAllByEvento_IdEventoOrderByTimestampLogDesc(Objects.requireNonNull(idEvento))
                .stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idBitacora) {
        bitacoraEventoRepository.delete(Objects.requireNonNull(getById(idBitacora)));
    }

    private BitacoraEvento getById(Long idBitacora) {
        return bitacoraEventoRepository.findById(Objects.requireNonNull(idBitacora))
                .orElseThrow(() -> new ResourceNotFoundException("Bitácora no encontrada: " + idBitacora));
    }

    private BitacoraEvento toEntity(BitacoraEventoRequest request) {
        BitacoraEvento bitacora = new BitacoraEvento();
        aplicar(bitacora, request);
        return bitacora;
    }

    private void aplicar(BitacoraEvento bitacora, BitacoraEventoRequest request) {
        Evento evento = eventoRepository.findById(Objects.requireNonNull(request.idEvento()))
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado: " + request.idEvento()));
        bitacora.setEvento(evento);
        if (request.idPersonal() != null) {
            bitacora.setIdPersonal(request.idPersonal());
        } else {
            bitacora.setIdPersonal(null);
        }
        bitacora.setTimestampLog(request.timestampLog());
        bitacora.setTipoRegistro(request.tipoRegistro() != null ? request.tipoRegistro() : TipoRegistroBitacora.NOVEDAD);
        bitacora.setDescripcion(request.descripcion());
    }

    private BitacoraEventoResponse toResponse(BitacoraEvento bitacora) {
        return new BitacoraEventoResponse(
                bitacora.getIdBitacora(),
                bitacora.getEvento().getIdEvento(),
                bitacora.getEvento().getNombreCelebrado(),
                bitacora.getIdPersonal() != null ? bitacora.getIdPersonal() : null,
                null, // Nombre completo del personal no disponible sin Feign
                bitacora.getTimestampLog(),
                bitacora.getTipoRegistro(),
                bitacora.getDescripcion()
        );
    }
}
