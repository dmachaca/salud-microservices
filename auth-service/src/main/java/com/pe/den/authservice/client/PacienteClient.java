package com.pe.den.authservice.client;

import com.pe.den.authservice.client.fallback.PacienteClientFallback;
import com.pe.den.authservice.config.FeignConfig;
import com.pe.den.authservice.model.dto.external.PacienteInputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paciente-service",
        fallback = PacienteClientFallback.class,
        configuration = FeignConfig.class)
public interface PacienteClient {

    @PostMapping("/v1/api/pacientes/internal/registrar-perfil")
    ResponseEntity<Long> registrarPerfilInterno(@RequestBody PacienteInputDto persona);
}