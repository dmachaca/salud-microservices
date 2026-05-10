package com.pe.den.gatewayservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties props;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(props.secret())
        );
    }

    public Claims validate(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String type = claims.get("type", String.class);

        if (!"ACCESS".equals(type)) {
            throw new RuntimeException("TOKEN NO ES ACCESS");
        }

        return claims;
    }
}