package com.pe.den.gatewayservice.security.filter;

import com.pe.den.gatewayservice.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/v1/api/auth/login",
            "/v1/api/auth/register",
            "/v3/api-docs",
            "/swagger-ui"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // =========================
        // 1. RUTAS PÚBLICAS
        // =========================
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        // =========================
        // 2. VALIDAR TOKEN OBLIGATORIO
        // =========================
        String auth = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (auth == null || !auth.startsWith("Bearer ")) {
            return unauthorized(exchange, "TOKEN FALTANTE");
        }

        String token = auth.substring(7);

        try {
            Claims claims = jwtService.validate(token);

            String username = claims.getSubject();
            String userId = claims.get("userId", String.class);
            String roles = String.valueOf(claims.get("roles"));

            log.info("TOKEN OK USER={}", username);

            ServerHttpRequest request = exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", userId)
                    .header("X-Username", username)
                    .header("X-Roles", roles)
                    .build();

            return chain.filter(exchange.mutate().request(request).build());

        } catch (Exception e) {
            log.error("TOKEN INVALIDO", e);
            return unauthorized(exchange, "TOKEN INVALIDO O EXPIRADO");
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}