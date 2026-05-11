package com.pe.den.personalservice.model.mapper;

import com.pe.den.personalservice.model.dto.request.personal.PersonalInputDto;
import com.pe.den.personalservice.model.dto.response.personal.PersonalOutputDto;
import com.pe.den.personalservice.model.entity.PersonalSalud;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PersonalMapper {

    // --- MAPEO DE ENTRADA (Input -> Entity) ---
    @Mapping(target = "establecimiento.id", source = "establecimientoId")
    @Mapping(target = "tipoPersonal.id", source = "tipoPersonalId")
    @Mapping(target = "personaId", ignore = true) // El Service obtendrá este ID de Feign
    @Mapping(target = "especialidades", ignore = true) // El Service armará la tabla intermedia
    PersonalSalud toEntity(PersonalInputDto dto);


    // --- MAPEO DE SALIDA (Entity + Datos Externos -> Response) ---
    // Recibe la entidad base y los datos que trajimos del paciente-service
    @Mapping(target = "tipoPersonal", source = "personal.tipoPersonal.nombre")
    @Mapping(target = "establecimiento", source = "personal.establecimiento.nombre")
    @Mapping(target = "nombreCompleto", source = "nombreCompleto")
    @Mapping(target = "dni", source = "dni")
    @Mapping(target = "especialidades", expression = "java(mapEspecialidades(personal))")
    PersonalOutputDto toResponse(PersonalSalud personal, String nombreCompleto, String dni);

    // Método default de MapStruct para extraer solo los nombres de las especialidades
    default Set<String> mapEspecialidades(PersonalSalud personal) {
        if (personal.getEspecialidades() == null || personal.getEspecialidades().isEmpty()) {
            return java.util.Collections.emptySet();
        }
        return personal.getEspecialidades().stream()
                .map(pe -> pe.getEspecialidad().getNombre())
                .collect(Collectors.toSet());
    }
}