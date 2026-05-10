package com.pe.den.citaservice.rest.controller;

import com.pe.den.citaservice.model.dto.response.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/cita")
@RequiredArgsConstructor
public class CitaController extends BaseController{
    /*
     * LOGIN
     */
    @GetMapping("/test")
    public ResponseEntity<GenericResponse> test( ) {

        return handleRequest(() -> {

            var result = "Prueba";

            GenericResponse response =
                    new GenericResponse();

            response.setSuccess(true);

            response.setMessage(
                    "Login exitoso"
            );

            response.setData(result);

            return response;
        });
    }
}
