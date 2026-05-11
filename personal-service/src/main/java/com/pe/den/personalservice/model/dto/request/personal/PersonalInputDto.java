package com.pe.den.personalservice.model.dto.request.personal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public record PersonalInputDto(
        // Datos de Identidad (Se enviarán al paciente-service vía Feign)
        @NotBlank @Size(min = 8, max = 8) String dni,
        @NotBlank String nombres,
        @NotBlank String apellidoPaterno,
        String apellidoMaterno,
        @NotNull LocalDate fechaNacimiento,
        String genero,
        String telefono,
        String direccion,

        // Datos Laborales (Se guardan en personal-service)
        @NotNull Long establecimientoId,
        @NotNull Long tipoPersonalId,
        @NotBlank String colegiatura,

        // Usamos Set por si un médico tiene más de una especialidad
        Set<Long> especialidadIds
) {}