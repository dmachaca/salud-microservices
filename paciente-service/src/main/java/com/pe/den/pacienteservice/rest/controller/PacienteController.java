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


    // ENDPOINT PARA EL FRONTEND
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

    /**
     * Endpoint para COMUNICACIÓN INTERNA (Microservicios).
     * El Auth-Service llamará a este endpoint vía Feign.
     */
    @PostMapping("/internal/registrar-perfil")
    public ResponseEntity<Long> registrarPerfilInterno(@Valid @RequestBody PacienteInputDto dto) {
        // Retornamos directamente el Long (persona_id) para facilitar la orquestación
        return ResponseEntity.ok(pacienteService.registrarPerfil(dto));
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

    @GetMapping("/{id}/exists")
    public ResponseEntity<GenericResponse> verificarExistencia(@PathVariable Long id) {
        return handleRequest(() -> {
            // Verificamos si el paciente existe y está activo
            boolean existe = pacienteService.existePaciente(id);

            GenericResponse response = new GenericResponse();
            response.setSuccess(existe);
            response.setMessage(existe ? "Paciente validado correctamente" : "El paciente no existe o está inactivo");

            return response;
        });
    }
}