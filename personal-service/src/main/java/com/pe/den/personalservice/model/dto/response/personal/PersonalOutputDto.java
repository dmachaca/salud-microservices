package com.pe.den.personalservice.model.dto.response.personal;

import java.util.Set;

public record PersonalOutputDto(
        Long id,
        Long personaId,           // La referencia lógica
        String dni,               // Inyectado desde el otro microservicio
        String nombreCompleto,    // Inyectado desde el otro microservicio
        String tipoPersonal,      // Ejemplo: "MEDICO"
        String establecimiento,   // Ejemplo: "HOSPITAL CENTRAL"
        String colegiatura,
        Set<String> especialidades, // Lista de nombres de especialidades
        Boolean activo
) {}