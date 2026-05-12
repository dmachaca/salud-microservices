package com.pe.den.authservice.client.fallback;

import com.pe.den.authservice.client.PacienteClient;
import com.pe.den.authservice.model.dto.external.PacienteInputDto;
import com.pe.den.authservice.model.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PacienteClientFallback implements PacienteClient {

    @Override
    public ResponseEntity<Long> registrarPerfilInterno(PacienteInputDto persona){
        GenericResponse response = new GenericResponse();
        response.setSuccess(false);
        response.setMessage("Servicio de Pacientes no disponible (Circuit Breaker)");
        response.setMessageInterno("Fallo en la comunicación con el microservicio de pacientes");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }
}