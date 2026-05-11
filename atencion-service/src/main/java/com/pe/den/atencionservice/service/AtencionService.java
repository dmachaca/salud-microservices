package com.pe.den.atencionservice.service;


import com.pe.den.atencionservice.model.dto.request.atencion.AtencionInputDto;
import com.pe.den.atencionservice.model.dto.response.atencion.AtencionOutputDto;

public interface AtencionService {
    AtencionOutputDto registrarAtencion(AtencionInputDto request);
    AtencionOutputDto obtenerPorCita(Long citaId);
}