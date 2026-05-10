package com.pe.den.authservice.rest.controller;

import com.pe.den.authservice.model.dto.request.auth.LoginInputDto;
import com.pe.den.authservice.model.dto.request.auth.RefreshTokenInputDto;
import com.pe.den.authservice.model.dto.response.GenericResponse;
import com.pe.den.authservice.security.jwt.JwtService;
import com.pe.den.authservice.security.service.SecurityService;


import com.pe.den.authservice.service.AuthService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;

    private final JwtService jwtService;

    private final SecurityService securityService;

    /*
     * LOGIN
     */
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(
            @RequestBody
            @Valid
            LoginInputDto request
    ) {

        return handleRequest(() -> {

            var result =
                    authService.login(request);

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

    /*
     * REFRESH TOKEN
     */
    @PostMapping("/refresh")
    public ResponseEntity<GenericResponse> refresh(
            @RequestBody
            @Valid
            RefreshTokenInputDto request
    ) {

        return handleRequest(() -> {

            var result =
                    authService.refreshToken(request);

            GenericResponse response =
                    new GenericResponse();

            response.setSuccess(true);

            response.setMessage(
                    "Token renovado"
            );

            response.setData(result);

            return response;
        });
    }

    /*
     * LOGOUT
     */
    @PostMapping("/logout")
    public ResponseEntity<GenericResponse> logout(
            @RequestBody
            @Valid
            RefreshTokenInputDto request
    ) {

        return handleRequest(() -> {

            String jti =
                    jwtService.extractJti(
                            request.refreshToken()
                    );

            authService.logout(jti);

            GenericResponse response =
                    new GenericResponse();

            response.setSuccess(true);

            response.setMessage(
                    "Logout exitoso"
            );

            return response;
        });
    }

    /*
     * LOGOUT GLOBAL
     */
    @PostMapping("/logout-all")
    public ResponseEntity<GenericResponse> logoutAll() {

        return handleRequest(() -> {

            Long userId =
                    securityService
                            .obtenerIdUsuarioActual();

            authService.logoutAll(userId);

            GenericResponse response =
                    new GenericResponse();

            response.setSuccess(true);

            response.setMessage(
                    "Logout global exitoso"
            );

            return response;
        });
    }
}