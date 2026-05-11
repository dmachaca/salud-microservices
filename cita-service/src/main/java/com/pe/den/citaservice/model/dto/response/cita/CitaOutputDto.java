package com.pe.den.citaservice.model.dto.response.cita;

import java.time.ZonedDateTime;

public record CitaOutputDto(
        Long id,
        Long pacienteId,
        Long personalId,
        Long establecimientoId,
        String estadoNombre,
        String tipoNombre,
        ZonedDateTime fechaHora,
        String motivo,
        String observacion,
        Boolean activo
) {}