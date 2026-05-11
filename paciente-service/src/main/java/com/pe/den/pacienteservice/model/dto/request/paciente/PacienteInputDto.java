package com.pe.den.pacienteservice.model.dto.request.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteInputDto(
        @NotBlank @Size(min = 8, max = 8) String dni,
                               @NotBlank String nombres,
                               @NotBlank String apellidoPaterno,
                               String apellidoMaterno,
                               LocalDate fechaNacimiento,
                               String genero,
                               String telefono,
                               String direccion
) {}