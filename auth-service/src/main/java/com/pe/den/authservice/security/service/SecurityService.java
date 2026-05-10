package com.pe.den.authservice.security.service;


import com.pe.den.authservice.exception.AccesoNoPermitidoException;
import com.pe.den.authservice.security.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public Long obtenerIdUsuarioActual() {
        return obtenerUsuarioActual()
                .getUsuario()
                .getId();
    }

    public String obtenerUsernameActual() {
        return obtenerUsuarioActual()
                .getUsername();
    }

    public Long obtenerPersonaIdActual() {
        return obtenerUsuarioActual()
                .getUsuario()
                .getPersonaId();
    }

    public String obtenerCorreoUsuarioActual() {
        return obtenerUsuarioActual()
                .getUsuario()
                .getCorreo();
    }

    public UserPrincipal obtenerUsuarioActual() {

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {

            throw new AccesoNoPermitidoException("Usuario no autenticado");
        }

        return principal;
    }

    public boolean estaAutenticado() {

        var authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserPrincipal;
    }
}