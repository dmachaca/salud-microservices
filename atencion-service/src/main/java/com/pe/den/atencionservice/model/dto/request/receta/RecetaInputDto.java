package com.pe.den.atencionservice.model.dto.request.receta;

import jakarta.validation.constraints.NotBlank;

public record RecetaInputDto(
        @NotBlank(message = "El nombre del medicamento es obligatorio")
        String medicamento,
        String dosis,
        String indicaciones,
        String duracion
) {}
