package com.pe.den.authservice.service.impl;

import com.pe.den.authservice.client.PacienteClient;
import com.pe.den.authservice.model.dto.request.usuario.UsuarioInputDto;
import com.pe.den.authservice.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.den.authservice.model.entity.Rol;
import com.pe.den.authservice.model.entity.Usuario;
import com.pe.den.authservice.model.entity.UsuarioRol;
import com.pe.den.authservice.repository.RolRepository;
import com.pe.den.authservice.repository.UsuarioRepository;
import com.pe.den.authservice.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final PacienteClient pacienteClient;

    @Override
    @Transactional
    public UsuarioOutputDto registrarPaciente(UsuarioInputDto request) {

        // 1. Validar correo local
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // 2. Feign Call - Obtener ID del micro Paciente
        ResponseEntity<Long> response = pacienteClient.registrarPerfilInterno(request.persona());
        Long personaId = response.getBody();

        if (personaId == null) {
            throw new RuntimeException("Error al crear el perfil: El servicio de pacientes no respondió.");
        }

        // 3. Crear Entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setPersonaId(personaId);
        usuario.setNombreUsuario(request.persona().dni());
        usuario.setCorreo(request.correo());
        usuario.setClaveHash(passwordEncoder.encode(request.clave()));
        usuario.setActivo(true);

        // 4. Gestionar Relación Intermedia
        // Usamos Integer (2) porque tu entidad Rol usa Integer id
        Rol rol = rolRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol PACIENTE (ID 2) no encontrado en la base de datos"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        // usuarioRol.setActivo(true); // Si BaseEntity lo requiere

        // 5. Vincular y Guardar
        usuario.setUsuarioRoles(Set.of(usuarioRol));
        Usuario guardado = usuarioRepository.save(usuario);

        // 6. Respuesta
        return new UsuarioOutputDto(
                guardado.getId(),
                personaId,
                guardado.getNombreUsuario(),
                guardado.getCorreo(),
                Set.of(rol.getNombre()),
                LocalDateTime.now()
        );
    }
}