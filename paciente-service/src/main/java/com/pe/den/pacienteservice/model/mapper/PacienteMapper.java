package com.pe.den.pacienteservice.model.mapper;
import com.pe.den.pacienteservice.model.dto.request.paciente.PacienteInputDto;
import com.pe.den.pacienteservice.model.dto.response.paciente.PacienteOutputDto;
import com.pe.den.pacienteservice.model.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    @Mapping(target = "persona.dni", source = "dni")
    @Mapping(target = "persona.nombres", source = "nombres")
    @Mapping(target = "persona.apellidoPaterno", source = "apellidoPaterno")
    @Mapping(target = "persona.apellidoMaterno", source = "apellidoMaterno")
    @Mapping(target = "persona.fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "persona.genero", source = "genero")
    @Mapping(target = "persona.telefono", source = "telefono")
    @Mapping(target = "persona.direccion", source = "direccion")
    Paciente toEntity(PacienteInputDto dto);

    @Mapping(target = "dni", source = "persona.dni")
    @Mapping(target = "nombreCompleto", expression = "java(p.getPersona().getNombres() + ' ' + p.getPersona().getApellidoPaterno())")
    PacienteOutputDto toResponse(Paciente p);
}