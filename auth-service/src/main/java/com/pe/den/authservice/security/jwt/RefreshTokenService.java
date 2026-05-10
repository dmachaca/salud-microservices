package com.pe.den.authservice.security.jwt;

import com.pe.den.authservice.model.entity.RefreshToken;
import com.pe.den.authservice.model.entity.Usuario;
import com.pe.den.authservice.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder encoder;

    public String create(Usuario usuario) {

        String rawToken = generateToken(); // secreto
        String jti = generateToken();      // identificador público

        String hash = hash(rawToken);

        RefreshToken entity = RefreshToken.builder()
                .usuario(usuario)
                .tokenHash(hash)
                .jti(jti)
                .expiracion(OffsetDateTime.now().plusDays(7))
                .revocado(false)
                .build();

        refreshTokenRepository.save(entity);

        return rawToken + "." + jti;
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RefreshToken validate(String fullToken) {

        String[] parts = fullToken.split("\\.");

        if (parts.length != 2) {
            throw new RuntimeException("TOKEN MAL FORMADO");
        }

        String rawToken = parts[0];
        String jti = parts[1];

        RefreshToken entity = refreshTokenRepository.findByJtiAndRevocadoFalse(jti)
                .orElseThrow(() -> new RuntimeException("TOKEN NO EXISTE"));

        if (entity.getExpiracion().isBefore(OffsetDateTime.now())) {
            throw new RuntimeException("TOKEN EXPIRADO");
        }

        if (!encoder.matches(rawToken, entity.getTokenHash())) {
            throw new RuntimeException("TOKEN INVALIDO");
        }

        return entity;
    }

    @Transactional
    public String rotate(RefreshToken token) {

        token.setRevocado(true);
        refreshTokenRepository.save(token);

        return create(token.getUsuario());
    }

    @Transactional
    public void revoke(String fullToken) {

        RefreshToken token = validate(fullToken);

        token.setRevocado(true);
        refreshTokenRepository.save(token);
    }

    @Transactional
    public void revokeAllByUser(Long userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
    }

    @Transactional
    public void cleanExpired() {
        refreshTokenRepository.deleteExpiredOrRevoked(OffsetDateTime.now());
    }

    private String generateToken() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}