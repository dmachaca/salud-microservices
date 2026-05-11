package com.pe.den.pacienteservice.service;

import com.pe.den.pacienteservice.model.dto.request.paciente.PacienteInputDto;
import com.pe.den.pacienteservice.model.dto.response.paciente.PacienteOutputDto;

public interface PacienteService {
    PacienteOutputDto registrar(PacienteInputDto dto);
    PacienteOutputDto buscarPorDni(String dni);
    boolean existePaciente(Long id);
}
