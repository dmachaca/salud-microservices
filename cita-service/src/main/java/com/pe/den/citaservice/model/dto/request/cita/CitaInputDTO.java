package com.pe.den.citaservice.model.dto.request.cita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public record CitaInputDTO(
        @NotNull(message = "El ID del paciente es obligatorio")
        Long pacienteId,

        @NotNull(message = "El ID del personal es obligatorio")
        Long personalId,

        @NotNull(message = "El ID del establecimiento es obligatorio")
        Long establecimientoId,

        @NotNull(message = "El tipo de cita es obligatorio")
        Integer tipoCitaId,

        @NotNull(message = "La fecha y hora son obligatorias")
        @Future(message = "La cita debe ser en una fecha futura")
        ZonedDateTime fechaHora,

        String motivo,
        String observacion
) {}