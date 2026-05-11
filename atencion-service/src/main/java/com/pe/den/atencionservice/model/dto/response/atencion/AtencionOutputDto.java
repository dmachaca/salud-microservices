package com.pe.den.atencionservice.model.dto.response.atencion;

import com.pe.den.atencionservice.model.dto.response.receta.RecetaOutputDto;

import java.time.ZonedDateTime;
import java.util.List;

public record AtencionOutputDto(
        Long id,
        Long citaId,
        String diagnosticoCie10,
        String diagnosticoDescripcion,
        String planTratamiento,
        String observaciones,
        ZonedDateTime fechaCreacion,
        List<RecetaOutputDto> recetas
) {
}
