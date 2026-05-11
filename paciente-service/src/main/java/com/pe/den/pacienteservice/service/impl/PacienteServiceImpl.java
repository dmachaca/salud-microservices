package com.pe.den.pacienteservice.service.impl;

import com.pe.den.pacienteservice.model.dto.request.paciente.PacienteInputDto;
import com.pe.den.pacienteservice.model.dto.response.paciente.PacienteOutputDto;
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
    private final PacienteMapper mapper;

    @Override
    @Transactional
    public PacienteOutputDto registrar(PacienteInputDto dto) {
        if (personaRepository.existsByDni(dto.dni())) { // Acceso al record con .dni()
            throw new RuntimeException("El DNI ya se encuentra registrado");
        }

        var paciente = mapper.toEntity(dto);
        return mapper.toResponse(pacienteRepository.save(paciente));
    }

    @Override
    public PacienteOutputDto buscarPorDni(String dni) {
        return pacienteRepository.findByDniWithPersona(dni)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }
}