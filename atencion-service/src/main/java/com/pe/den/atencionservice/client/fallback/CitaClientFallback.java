package com.pe.den.atencionservice.client.fallback;

import com.pe.den.atencionservice.client.CitaClient;
import com.pe.den.atencionservice.model.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CitaClientFallback implements CitaClient {

    @Override
    public ResponseEntity<GenericResponse> obtenerCitaPorId(Long id) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Servicio de Cita no disponible (Circuit Breaker)");
        response.setMessageInterno("Fallo en la comunicación con el microservicio de citas");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}