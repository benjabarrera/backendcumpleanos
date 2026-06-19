package com.micumpleanos.cliente.application.dto.cliente;

public record ClienteResponse(
        Long idCliente,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String direccion,
        String ciudad,
        String notas,
        Boolean activo
) {
}
