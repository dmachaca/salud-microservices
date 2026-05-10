package com.pe.den.gatewayservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain security(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex

                        // auth libre
                        .pathMatchers("/v1/api/auth/**").permitAll()

                        // swagger
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // todo lo demás protegido
                        .anyExchange().authenticated()
                )
                .build();
    }
}