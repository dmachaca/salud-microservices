package com.pe.den.citaservice.rest.controller;

import com.pe.den.citaservice.model.dto.request.cita.CitaInputDTO;
import com.pe.den.citaservice.model.dto.response.GenericResponse;
import com.pe.den.citaservice.model.dto.response.cita.CitaOutputDto;
import com.pe.den.citaservice.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/cita")
@RequiredArgsConstructor
public class CitaController extends BaseController{

    private final CitaService citaService;

    @GetMapping("/test")
    public ResponseEntity<GenericResponse> test( ) {

        return handleRequest(() -> {
            var result = "Prueba";
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Login exitoso");
            response.setData(result);
            return response;
        });
    }

    @GetMapping("/health")
    public ResponseEntity<GenericResponse> health() {
        return handleRequest(() -> {
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Cita-Service is UP");
            response.setData("OK"); // O puedes pasar un objeto con detalles del sistema
            return response;
        });
    }

    @PostMapping("/registrar")
    public ResponseEntity<GenericResponse> registrar(@Valid @RequestBody CitaInputDTO request) {
        return handleRequest(() -> {
            CitaOutputDto data = citaService.registrarCita(request);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Cita programada exitosamente");
            response.setData(data);

            return response;
        });
    }
}
