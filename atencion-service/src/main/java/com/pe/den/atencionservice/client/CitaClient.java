package com.pe.den.atencionservice.client;

import com.pe.den.atencionservice.client.fallback.CitaClientFallback;
import com.pe.den.atencionservice.config.FeignConfig;
import com.pe.den.atencionservice.model.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cita-service", fallback = CitaClientFallback.class, configuration = FeignConfig.class)
public interface CitaClient {

    @GetMapping("/v1/api/cita/{id}")
    ResponseEntity<GenericResponse> obtenerCitaPorId(@PathVariable("id") Long id);
}