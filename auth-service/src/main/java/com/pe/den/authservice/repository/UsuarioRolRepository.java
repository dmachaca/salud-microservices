package com.pe.den.authservice.repository;

import com.pe.den.authservice.model.entity.Usuario;
import com.pe.den.authservice.model.entity.UsuarioRol;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolRepository
        extends JpaRepository<UsuarioRol, Long> {

    List<UsuarioRol> findByUsuario(Usuario usuario);

    Optional<UsuarioRol> findByUsuarioIdAndRolId(
            Long usuarioId,
            Integer rolId
    );

    boolean existsByUsuarioIdAndRolId(
            Long usuarioId,
            Integer rolId
    );
}