package com.pe.den.pacienteservice.model.dto.response.paciente;

public record PacienteOutputDto(
        Long id,
        String dni,
        String nombreCompleto,
        String telefono,
        Boolean activo
) {}