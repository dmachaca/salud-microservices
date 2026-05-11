package com.pe.den.citaservice.model.mapper;

import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;
import com.pe.den.citaservice.model.entity.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CitaMapper {

    // De DTO a Entidad (para guardar)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estadoCita", ignore = true) // Se asigna en el Service como PENDIENTE
    @Mapping(target = "tipoCita.id", source = "tipoCitaId")
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "creadoPorId", ignore = true)
    @Mapping(target = "actualizadoPorId", ignore = true)
    Cita toEntity(CitaInputDTO dto);

    // De Entidad a DTO (para mostrar)
    @Mapping(target = "estadoNombre", source = "estadoCita.nombre")
    @Mapping(target = "tipoNombre", source = "tipoCita.nombre")
    CitaOutputDto toDto(Cita entity);
}