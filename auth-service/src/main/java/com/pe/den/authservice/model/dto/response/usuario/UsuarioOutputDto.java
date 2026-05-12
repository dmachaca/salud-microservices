package com.pe.den.authservice.model.dto.response.usuario;

import java.time.LocalDateTime;
import java.util.Set;

public record UsuarioOutputDto(
        Long id,                // ID del usuario en auth_db
        Long personaId,         // El ID vinculado de paciente_db
        String nombreUsuario,   // El DNI que se usó como login
        String correo,
        Set<String> roles,      // ["ROLE_PACIENTE"]
        LocalDateTime fechaRegistro
) {}
