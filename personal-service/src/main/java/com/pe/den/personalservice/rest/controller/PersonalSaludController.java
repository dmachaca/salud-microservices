package com.pe.den.personalservice.rest.controller;

import com.pe.den.personalservice.model.dto.request.personal.PersonalInputDto;
import com.pe.den.personalservice.model.dto.response.GenericResponse;
import com.pe.den.personalservice.service.PersonalSaludService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/personal")
@RequiredArgsConstructor
public class PersonalSaludController extends BaseController{

    private final PersonalSaludService personalService;

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
    public ResponseEntity<GenericResponse> registrar(@Valid @RequestBody PersonalInputDto dto) {
        return handleRequest(() -> {
            var data = personalService.registrarPersonal(dto);

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Personal de salud registrado correctamente");
            response.setData(data);
            return response;
        });
    }

    @GetMapping("/listar")
    public ResponseEntity<GenericResponse> listarTodo() {
        return handleRequest(() -> {
            var lista = personalService.listarTodo();

            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Listado obtenido con éxito");
            response.setData(lista);
            return response;
        });
    }


}