package com.pe.den.pacienteservice.service.impl;

import com.pe.den.pacienteservice.model.dto.request.paciente.PacienteInputDto;
import com.pe.den.pacienteservice.model.dto.response.paciente.PacienteOutputDto;
import com.pe.den.pacienteservice.model.entity.Paciente;
import com.pe.den.pacienteservice.model.entity.Persona;
import com.pe.den.pacienteservice.model.mapper.PacienteMapper;
import com.pe.den.pacienteservice.repository.PacienteRepository;
import com.pe.den.pacienteservice.repository.PersonaRepository;
import com.pe.den.pacienteservice.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PersonaRepository personaRepository;
    private final PacienteMapper pacienteMapper;

    @Override
    @Transactional
    public PacienteOutputDto registrar(PacienteInputDto dto) {
        // 1. Validación de negocio (Evitar DNI duplicado)
        if (personaRepository.existsByDni(dto.dni())) {
            throw new RuntimeException("El DNI " + dto.dni() + " ya se encuentra registrado");
        }

        // 2. Mapeo a entidad (MapStruct crea el objeto Persona dentro de Paciente)
        Paciente paciente = pacienteMapper.toEntity(dto);

        // 3. Persistencia de Persona: Necesario para que la DB genere el ID
        Persona personaGuardada = personaRepository.save(paciente.getPersona());

        // 4. Vínculo: Seteamos la persona guardada (con ID) al paciente
        paciente.setPersona(personaGuardada);

        // 5. Persistencia de Paciente
        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        // 6. Retornar DTO de respuesta completo
        return pacienteMapper.toResponse(pacienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteOutputDto buscarPorDni(String dni) {
        return pacienteRepository.findByDniWithPersona(dni)
                .map(pacienteMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con DNI: " + dni));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePaciente(Long id) {
        return pacienteRepository.existsByIdAndActivoTrue(id);
    }

    @Override
    @Transactional
    public Long registrarPerfil(PacienteInputDto dto) {
        // Ahora simplemente llamamos al método principal y extraemos el ID
        // Esto mantiene la lógica centralizada en un solo lugar
        PacienteOutputDto output = registrar(dto);
        return output.id(); // Retorna el persona_id para el Auth-Service
    }
}