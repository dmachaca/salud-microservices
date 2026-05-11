package com.pe.den.citaservice.service;

import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;

public interface CitaService {
    CitaOutputDto registrarCita(CitaInputDTO request);
    CitaOutputDto obtenerCitaPorId(Long id);
}
