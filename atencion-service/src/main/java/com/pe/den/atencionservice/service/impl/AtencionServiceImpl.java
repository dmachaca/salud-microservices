package com.pe.den.atencionservice.service.impl;

import com.pe.den.atencionservice.client.CitaClient;
import com.pe.den.atencionservice.config.auditoria.AuditoriaInterceptor;
import com.pe.den.atencionservice.exception.BusinessException;
import com.pe.den.atencionservice.model.dto.request.atencion.AtencionInputDto;
import com.pe.den.atencionservice.model.dto.response.GenericResponse;
import com.pe.den.atencionservice.model.dto.response.atencion.AtencionOutputDto;
import com.pe.den.atencionservice.model.entity.Atencion;
import com.pe.den.atencionservice.model.mapper.AtencionMapper;
import com.pe.den.atencionservice.repository.AtencionRepository;
import com.pe.den.atencionservice.service.AtencionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtencionServiceImpl implements AtencionService {

    private final AtencionRepository atencionRepository;
    private final AtencionMapper atencionMapper;

    private final CitaClient citaClient;
    private final AuditoriaInterceptor auditoriaInterceptor;


    @Override
    @Transactional
    @CircuitBreaker(name = "cita-service", fallbackMethod = "fallbackRegistrarAtencion")
    public AtencionOutputDto registrarAtencion(AtencionInputDto request) {

        // 1. Configurar auditoría en la conexión actual (suponiendo que tienes el ID del médico)
        // 1. SETEAR AUDITORÍA: Fundamental para que el trigger de atencion-db funcione
        auditoriaInterceptor.setUsuarioAuditoria(request.usuarioId(), "trace-atn-" + request.citaId());

        log.info("Validando cita {} con cita-service", request.citaId());

        ResponseEntity<GenericResponse> responseCita = citaClient.obtenerCitaPorId(request.citaId());

        if (responseCita.getStatusCode().isError() || responseCita.getBody() == null || !responseCita.getBody().getSuccess()) {
            throw new BusinessException("No se puede proceder: La cita no es válida o el servicio no respondió correctamente.");
        }

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

    // Método Fallback para registrarAtencion
    public AtencionOutputDto fallbackRegistrarAtencion(AtencionInputDto request, Throwable t) {
        log.error("Circuit Breaker activo para registrarAtencion. Motivo: {}", t.getMessage());
        throw new RuntimeException("El sistema de validación de citas no está disponible. " +
                "Por seguridad, no se puede registrar la atención en este momento.");
    }

    @Override
    @Transactional(readOnly = true)
    public AtencionOutputDto obtenerPorCita(Long citaId) {
        Atencion atencion = atencionRepository.findByCitaId(citaId)
                .orElseThrow(() -> new RuntimeException("No se encontró atención para la cita: " + citaId));
        return atencionMapper.toDto(atencion);
    }
}