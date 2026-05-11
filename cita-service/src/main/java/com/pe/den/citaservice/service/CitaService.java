package com.pe.den.citaservice.service;

import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CitaService {
    CitaOutputDto registrarCita(CitaInputDTO request);
    CitaOutputDto obtenerCitaPorId(Long id);
    Page<CitaOutputDto> listarCitasPorPaciente(Long pacienteId, Pageable pageable);
}
