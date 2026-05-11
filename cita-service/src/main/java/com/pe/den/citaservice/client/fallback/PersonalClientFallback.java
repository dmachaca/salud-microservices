package com.pe.den.citaservice.client.fallback;

import com.pe.den.citaservice.client.PersonalClient;
import com.pe.den.citaservice.model.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonalClientFallback implements PersonalClient {
    @Override
    public ResponseEntity<GenericResponse> verificarExistencia(Long id) {
        // Si el microservicio de Personal está caído, devolvemos un error controlado
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Servicio de validación de personal no disponible (Circuit Breaker)");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}