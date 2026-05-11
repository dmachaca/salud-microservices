package com.pe.den.pacienteservice.rest.controller;


import com.pe.den.pacienteservice.model.dto.request.paciente.PacienteInputDto;
import com.pe.den.pacienteservice.model.dto.response.GenericResponse;
import com.pe.den.pacienteservice.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/pacientes")
@RequiredArgsConstructor
public class PacienteController extends BaseController{

    private final PacienteService pacienteService;

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
    public ResponseEntity<GenericResponse> registrar(@Valid @RequestBody PacienteInputDto dto) {
        return handleRequest(() -> {
            var data = pacienteService.registrar(dto);
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Paciente registrado con éxito");
            response.setData(data);
            return response;
        });
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<GenericResponse> buscarPorDni(@PathVariable String dni) {
        return handleRequest(() -> {
            var data = pacienteService.buscarPorDni(dni);
            GenericResponse response = new GenericResponse();
            response.setSuccess(true);
            response.setMessage("Paciente encontrado");
            response.setData(data);
            return response;
        });
    }
}