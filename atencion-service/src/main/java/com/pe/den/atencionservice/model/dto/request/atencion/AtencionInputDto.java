package com.pe.den.atencionservice.model.dto.request.atencion;

import com.pe.den.atencionservice.model.dto.request.receta.RecetaInputDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AtencionInputDto(
        @NotNull(message = "El ID de la cita es obligatorio")
        Long citaId,

        @Size(max = 10, message = "El código CIE10 no debe exceder los 10 caracteres")
        String diagnosticoCie10,

        @NotBlank(message = "La descripción del diagnóstico es obligatoria")
        String diagnosticoDescripcion,

        String planTratamiento,
        String observaciones,

        @Valid // Para validar cada receta dentro de la lista
        List<RecetaInputDto> recetas
) {}
