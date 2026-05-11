package com.pe.den.atencionservice.model.dto.response.receta;

public record RecetaOutputDto(
        Long id,
        String medicamento,
        String dosis,
        String indicaciones,
        String duracion
) {
}
