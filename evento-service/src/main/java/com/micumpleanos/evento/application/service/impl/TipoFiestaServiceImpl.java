package com.micumpleanos.evento.application.service.impl;

import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaRequest;
import com.micumpleanos.evento.application.dto.tipofiesta.TipoFiestaResponse;
import com.micumpleanos.evento.application.service.TipoFiestaService;
import com.micumpleanos.evento.domain.entity.TipoFiesta;
import com.micumpleanos.evento.domain.repository.TipoFiestaRepository;
import com.micumpleanos.evento.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TipoFiestaServiceImpl implements TipoFiestaService {

    private final TipoFiestaRepository tipoFiestaRepository;

    @Override
    public TipoFiestaResponse crear(TipoFiestaRequest request) {
        return toResponse(tipoFiestaRepository.save(Objects.requireNonNull(toEntity(request))));
    }

    @Override
    public TipoFiestaResponse actualizar(Long idTipoFiesta, TipoFiestaRequest request) {
        TipoFiesta tipoFiesta = getById(idTipoFiesta);
        aplicar(tipoFiesta, request);
        return toResponse(tipoFiestaRepository.save(Objects.requireNonNull(tipoFiesta)));
    }

    @Override
    @Transactional(readOnly = true)
    public TipoFiestaResponse buscarPorId(Long idTipoFiesta) {
        return toResponse(getById(idTipoFiesta));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoFiestaResponse> listar() {
        return tipoFiestaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void eliminar(Long idTipoFiesta) {
        tipoFiestaRepository.delete(Objects.requireNonNull(getById(idTipoFiesta)));
    }

    private TipoFiesta getById(Long idTipoFiesta) {
        return tipoFiestaRepository.findById(Objects.requireNonNull(idTipoFiesta))
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de fiesta no encontrado: " + idTipoFiesta));
    }

    private TipoFiesta toEntity(TipoFiestaRequest request) {
        TipoFiesta tipoFiesta = new TipoFiesta();
        aplicar(tipoFiesta, request);
        return tipoFiesta;
    }

    private void aplicar(TipoFiesta tipoFiesta, TipoFiestaRequest request) {
        tipoFiesta.setNombre(request.nombre());
        tipoFiesta.setDescripcion(request.descripcion());
        if (request.colorHex() != null) {
            tipoFiesta.setColorHex(request.colorHex());
        }
        if (request.activo() != null) {
            tipoFiesta.setActivo(request.activo());
        }
    }

    private TipoFiestaResponse toResponse(TipoFiesta tipoFiesta) {
        return new TipoFiestaResponse(
                tipoFiesta.getIdTipoFiesta(), tipoFiesta.getNombre(), tipoFiesta.getDescripcion(),
                tipoFiesta.getColorHex(), tipoFiesta.getActivo()
        );
    }
}
