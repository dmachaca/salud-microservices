package com.pe.den.atencionservice.model.mapper;

import com.pe.den.atencionservice.model.dto.request.atencion.AtencionInputDto;
import com.pe.den.atencionservice.model.dto.response.atencion.AtencionOutputDto;
import com.pe.den.atencionservice.model.entity.Atencion;
import org.mapstruct.*;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AtencionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "creadoPorId", ignore = true)
    // CORRECCIÓN 1: Usar el nombre de la variable en la Entidad (camelCase)
    @Mapping(target = "actualizadoPorId", ignore = true)
    Atencion toEntity(AtencionInputDto dto);

    AtencionOutputDto toDto(Atencion entity);

    // CORRECCIÓN 2: Método para convertir entre tipos de fecha
    default ZonedDateTime map(OffsetDateTime value) {
        return value == null ? null : value.toZonedDateTime();
    }

    default OffsetDateTime map(ZonedDateTime value) {
        return value == null ? null : value.toOffsetDateTime();
    }

    @AfterMapping
    default void linkRecetas(@MappingTarget Atencion atencion) {
        if (atencion.getRecetas() != null) {
            atencion.getRecetas().forEach(receta -> receta.setAtencion(atencion));
        }
    }
}