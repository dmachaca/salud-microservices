package com.pe.den.citaservice.client;

import com.pe.den.citaservice.client.fallback.PacienteClientFallback;
import com.pe.den.citaservice.config.FeignConfig;
import com.pe.den.citaservice.model.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "paciente-service", fallback = PacienteClientFallback.class, configuration = FeignConfig.class)
public interface PacienteClient {

    @GetMapping("/v1/api/pacientes/{id}/exists")
    ResponseEntity<GenericResponse> verificarExistencia(@PathVariable("id") Long id);
}