package com.pe.den.atencionservice.rest.controller;

import com.pe.den.atencionservice.model.dto.request.atencion.AtencionInputDto;
import com.pe.den.atencionservice.model.dto.response.GenericResponse;
import com.pe.den.atencionservice.model.dto.response.atencion.AtencionOutputDto;
import com.pe.den.atencionservice.service.AtencionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/atenciones")
@RequiredArgsConstructor
public class AtencionController extends BaseController{

    private final AtencionService atencionService;

    @GetMapping("/test")
    public ResponseEntity<GenericResponse> test( ) {

        return handleRequest(() -> {

            var result = "Prueba";
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Login exitoso" );
            response.setData(result);
            return response;
        });
    }

    @PostMapping("/registrar")
    public ResponseEntity<GenericResponse> registrar(@Valid @RequestBody AtencionInputDto request) {
        return handleRequest(() -> {
            AtencionOutputDto data = atencionService.registrarAtencion(request);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Atención y recetas registradas correctamente");
            response.setData(data);

            return response;
        });
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<GenericResponse> obtenerPorCita(@PathVariable Long citaId) {
        return handleRequest(() -> {
            AtencionOutputDto data = atencionService.obtenerPorCita(citaId);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Atención encontrada");
            response.setData(data);

            return response;
        });
    }

    @GetMapping("/health")
    public ResponseEntity<GenericResponse> health() {
        return handleRequest(() -> {
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Atencion-Service is UP");
            response.setData("OK");
            return response;
        });
    }
}