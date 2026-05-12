package com.pe.den.authservice.model.dto.external;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteInputDto(
        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
        @Pattern(regexp = "\\d+", message = "El DNI debe contener solo números")
        String dni,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombres,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 100)
        String apellidoPaterno,

        @Size(max = 100)
        String apellidoMaterno,

        @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
        LocalDate fechaNacimiento,

        @Pattern(regexp = "[MF]", message = "El género debe ser M o F")
        String genero,

        @Size(max = 20)
        String telefono,

        @Size(max = 255)
        String direccion
) {}