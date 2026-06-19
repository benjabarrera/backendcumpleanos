package com.micumpleanos.personal.application.dto.personal;

public record PersonalResponse(
        Long idPersonal,
        Long idRol,
        String rolNombre,
        String nombre,
        String apellido,
        String email,
        String telefono,
        String rut,
        Boolean activo
) {
}
