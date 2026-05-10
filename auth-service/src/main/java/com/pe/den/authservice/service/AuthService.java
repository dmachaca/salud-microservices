package com.pe.den.authservice.service;

import com.pe.den.authservice.model.dto.request.auth.LoginInputDto;
import com.pe.den.authservice.model.dto.request.auth.RefreshTokenInputDto;
import com.pe.den.authservice.model.dto.response.auth.AuthOutputDto;

public interface AuthService {

    AuthOutputDto login(LoginInputDto request);
    AuthOutputDto refreshToken(RefreshTokenInputDto request);
    void logout(String refreshToken);
    void logoutAll(Long userId); // opcional
}