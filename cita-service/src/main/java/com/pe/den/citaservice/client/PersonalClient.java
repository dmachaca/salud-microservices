package com.pe.den.citaservice.client;

import com.pe.den.citaservice.config.FeignConfig;
import com.pe.den.citaservice.model.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pe.den.citaservice.client.fallback.PersonalClientFallback;

@FeignClient(name = "personal-service", fallback = PersonalClientFallback.class, configuration = FeignConfig.class)
public interface PersonalClient {
    @GetMapping("/v1/api/personal/{id}/exists")
    ResponseEntity<GenericResponse> verificarExistencia(@PathVariable("id") Long id);
}