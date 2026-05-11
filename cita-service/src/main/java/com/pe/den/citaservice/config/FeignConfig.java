package com.pe.den.citaservice.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                // 1. Propagar el ID del Usuario (ESTO ES LO QUE BUSCA TU FILTER)
                String userId = request.getHeader("X-User-Id");
                if (userId != null) {
                    requestTemplate.header("X-User-Id", userId);
                }

                // 2. Propagar el Token JWT (Para seguridad de Spring Security)
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    requestTemplate.header("Authorization", authHeader);
                }

                // 3. Propagar Roles si tuvieras (X-User-Roles)
                String userRoles = request.getHeader("X-User-Roles");
                if (userRoles != null) {
                    requestTemplate.header("X-User-Roles", userRoles);
                }
            }
        };
    }
}