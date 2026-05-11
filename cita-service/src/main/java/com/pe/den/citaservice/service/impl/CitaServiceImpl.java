package com.pe.den.citaservice.service.impl;

import com.pe.den.citaservice.client.PacienteClient;
import com.pe.den.citaservice.client.PersonalClient;
import com.pe.den.citaservice.exception.BusinessException;
import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.GenericResponse;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;
import com.pe.den.citaservice.model.entity.Cita;
import com.pe.den.citaservice.model.mapper.CitaMapper;
import com.pe.den.citaservice.repository.CitaRepository;
import com.pe.den.citaservice.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    private final PersonalClient personalClient;
    private final PacienteClient pacienteClient;

    @Override
    @Transactional
    public CitaOutputDto registrarCita(CitaInputDTO request) {

        // 1. Validar Médico (Personal-Service)
        ResponseEntity<GenericResponse> respPersonal = personalClient.verificarExistencia(request.personalId());
        if (respPersonal.getBody() == null || !respPersonal.getBody().getSuccess()) {
            throw new BusinessException("Médico no válido: " + respPersonal.getBody().getMessage());
        }
        // 2. Validar Paciente (Paciente-Service) <-- ESTO ES LO QUE SIGUE
        ResponseEntity<GenericResponse> respPaciente = pacienteClient.verificarExistencia(request.pacienteId());
        if (respPaciente.getBody() == null || !respPaciente.getBody().getSuccess()) {
            throw new BusinessException("Paciente no válido: " + respPaciente.getBody().getMessage());
        }

        // 2. Mapear DTO a Entidad
        Cita cita = citaMapper.toEntity(request);

        // 3. Asignar Estado Inicial 'PENDIENTE'
        var estado = citaRepository.findEstadoByNombre("PENDIENTE")
                .orElseThrow(() -> new RuntimeException("Error: Estado PENDIENTE no encontrado en la base de datos."));
        cita.setEstadoCita(estado);

        // 4. Guardar (El trigger de auditoría en la DB hará el resto)
        Cita guardada = citaRepository.save(cita);

        return citaMapper.toDto(guardada);
    }
}