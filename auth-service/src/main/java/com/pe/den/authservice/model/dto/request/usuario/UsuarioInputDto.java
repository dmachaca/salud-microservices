package com.pe.den.authservice.model.dto.request.usuario;

import com.pe.den.authservice.model.dto.external.PacienteInputDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioInputDto(
        @Valid
        PacienteInputDto persona,

        // Datos de la Cuenta (Auth)
        @NotBlank
        @Email(message = "Debe ser un correo válido")
        String correo,

        @NotBlank
        @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
        String clave
) {}