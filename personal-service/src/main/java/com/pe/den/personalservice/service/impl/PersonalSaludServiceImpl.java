package com.pe.den.personalservice.service.impl;

import com.pe.den.personalservice.model.dto.request.personal.PersonalInputDto;
import com.pe.den.personalservice.model.dto.response.personal.PersonalOutputDto;
import com.pe.den.personalservice.model.entity.PersonalEspecialidad;
import com.pe.den.personalservice.model.entity.PersonalSalud;
import com.pe.den.personalservice.model.mapper.PersonalMapper;
import com.pe.den.personalservice.repository.EspecialidadRepository;
import com.pe.den.personalservice.repository.PersonalSaludRepository;
import com.pe.den.personalservice.service.PersonalSaludService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalSaludServiceImpl implements PersonalSaludService {
    private final PersonalSaludRepository personalSaludRepository;
    private final EspecialidadRepository especialidadRepository;
    private final PersonalMapper mapper;
    // private final PersonaClient personaClient; // Cliente Feign para el microservicio de pacientes

    @Transactional
    public PersonalOutputDto registrarPersonal(PersonalInputDto dto) {
        // 1. Lógica Profesional: Verificar si la colegiatura ya existe
        if (personalSaludRepository.existsByColegiatura(dto.colegiatura())) {
            throw new RuntimeException("La colegiatura ya está registrada");
        }

        // 2. Comunicación Inter-servicio (Simulada hasta tener Feign)
        // Aquí llamarías a: Long personaId = personaClient.guardarOObtenerPersona(dto.toPersonaDto());
        Long personaId = 100L; // ID temporal de ejemplo

        // 3. Mapeo y persistencia local
        PersonalSalud entidad = mapper.toEntity(dto);
        entidad.setPersonaId(personaId);

        // 4. Manejo de Especialidades (Tabla Intermedia)
        if (dto.especialidadIds() != null) {
            dto.especialidadIds().forEach(espId -> {
                var especialidad = especialidadRepository.findById(espId)
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada: " + espId));

                PersonalEspecialidad relacion = new PersonalEspecialidad();
                relacion.setPersonal(entidad);
                relacion.setEspecialidad(especialidad);
                entidad.getEspecialidades().add(relacion);
            });
        }

        PersonalSalud guardado = personalSaludRepository.save(entidad);

        // 5. Retornar con datos "aplanados" (En producción aquí pedimos nombres al otro microservicio)
        return mapper.toResponse(guardado, dto.nombres() + " " + dto.apellidoPaterno(), dto.dni());
    }

    @Transactional(readOnly = true)
    public List<PersonalOutputDto> listarTodo() {
        return personalSaludRepository.findAll().stream()
                .map(p -> mapper.toResponse(p, "Nombre Externo", "DNI Externo"))
                .collect(Collectors.toList());
    }
}
