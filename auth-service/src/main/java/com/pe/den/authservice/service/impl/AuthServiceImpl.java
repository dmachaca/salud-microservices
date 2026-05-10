package com.pe.den.authservice.service.impl;

import com.pe.den.authservice.model.dto.request.auth.LoginInputDto;
import com.pe.den.authservice.model.dto.request.auth.RefreshTokenInputDto;
import com.pe.den.authservice.model.dto.response.auth.AuthOutputDto;
import com.pe.den.authservice.model.entity.RefreshToken;
import com.pe.den.authservice.security.jwt.JwtService;
import com.pe.den.authservice.security.jwt.RefreshTokenService;
import com.pe.den.authservice.security.model.UserPrincipal;
import com.pe.den.authservice.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshService;

    @Override
    @Transactional
    public AuthOutputDto login(LoginInputDto request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var principal = (UserPrincipal) authentication.getPrincipal();
        var usuario = principal.getUsuario();

        String accessToken = jwtService.generateAccessToken(usuario);
        String refreshToken = refreshService.create(usuario);

        return new AuthOutputDto(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthOutputDto refreshToken(RefreshTokenInputDto request) {

        RefreshToken tokenEntity = refreshService.validate(request.refreshToken());

        String newAccess = jwtService.generateAccessToken(tokenEntity.getUsuario());

        String newRefresh = refreshService.rotate(tokenEntity);

        return new AuthOutputDto(newAccess, newRefresh);
    }

    // =========================
    //  LOGOUT (un dispositivo)
    // =========================
    @Override
    @Transactional
    public void logout(String refreshToken) {
        refreshService.revoke(refreshToken);
    }

    // =========================
    // LOGOUT GLOBAL
    // =========================
    @Override
    @Transactional
    public void logoutAll(Long userId) {
        refreshService.revokeAllByUser(userId);
    }
}