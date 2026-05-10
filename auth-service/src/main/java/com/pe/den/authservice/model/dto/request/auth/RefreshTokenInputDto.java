package com.pe.den.authservice.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenInputDto(
        @NotBlank String refreshToken,
        @NotNull Long userId
) {}