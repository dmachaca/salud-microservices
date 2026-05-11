package com.pe.den.atencionservice.service.impl;

import com.pe.den.atencionservice.model.dto.request.atencion.AtencionInputDto;
import com.pe.den.atencionservice.model.dto.response.atencion.AtencionOutputDto;
import com.pe.den.atencionservice.model.entity.Atencion;
import com.pe.den.atencionservice.model.mapper.AtencionMapper;
import com.pe.den.atencionservice.repository.AtencionRepository;
import com.pe.den.atencionservice.service.AtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;
    private final AtencionMapper atencionMapper;

    @Override
    @Transactional
    public AtencionOutputDto registrarAtencion(AtencionInputDto request) {

        // 1. Validar si la cita ya fue atendida
        if (atencionRepository.existsByCitaIdAndActivoTrue(request.citaId())) {
            throw new RuntimeException("Esta cita ya cuenta con un registro de atención médica.");
        }

        // 2. Mapear DTO a Entidad (El Mapper vincula las recetas automáticamente)
        Atencion atencion = atencionMapper.toEntity(request);

        // 3. Guardar (Cascada: guarda Atención y luego Recetas)
        Atencion guardada = atencionRepository.save(atencion);

        return atencionMapper.toDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AtencionOutputDto obtenerPorCita(Long citaId) {
        Atencion atencion = atencionRepository.findByCitaId(citaId)
                .orElseThrow(() -> new RuntimeException("No se encontró atención para la cita: " + citaId));
        return atencionMapper.toDto(atencion);
    }
}