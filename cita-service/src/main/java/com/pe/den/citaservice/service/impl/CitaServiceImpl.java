package com.pe.den.citaservice.service.impl;

import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;
import com.pe.den.citaservice.model.entity.Cita;
import com.pe.den.citaservice.model.mapper.CitaMapper;
import com.pe.den.citaservice.repository.CitaRepository;
import com.pe.den.citaservice.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    @Override
    @Transactional
    public CitaOutputDto registrarCita(CitaInputDTO request) {

        // 1. Validar disponibilidad del médico
        if (citaRepository.existsByPersonalIdAndFechaHoraAndActivoTrue(request.personalId(), request.fechaHora())) {
            throw new RuntimeException("El personal ya tiene una cita programada para esa fecha y hora.");
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