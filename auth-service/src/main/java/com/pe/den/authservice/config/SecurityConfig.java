package com.pe.den.authservice.config;

import com.pe.den.authservice.security.filter.JwtAuthenticationFilter;
import com.pe.den.authservice.security.handler.JwtAuthenticationEntryPoint;
import com.pe.den.authservice.security.jwt.JwtProperties;
import com.pe.den.authservice.security.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http

                /*
                 * CSRF
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * CORS
                 */
                .cors(cors -> {
                })

                /*
                 * SESSIONLESS
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                 * EXCEPTION HANDLER
                 */
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                /*
                 * AUTHORIZATION
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * AUTH
                         */
                        .requestMatchers("/v1/api/auth/**").permitAll()
                        /*
                         * SWAGGER
                         */
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/actuator/**").permitAll()

                        /*
                         * OPTIONS
                         */
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        /*
                         * ADMIN
                         */
                        .requestMatchers(
                                "/api/v1/admin/**"
                        ).hasRole("ADMIN")

                        /*
                         * DOCTOR
                         */
                        .requestMatchers(
                                "/api/v1/doctor/**"
                        ).hasAnyRole(  "DOCTOR",   "ADMIN"
                        )

                        /*
                         * PACIENTE
                         */
                        .requestMatchers(
                                "/api/v1/paciente/**"
                        ).hasAnyRole("PACIENTE",  "ADMIN"    )

                        /*
                         * TODO LO DEMÁS
                         */
                        .anyRequest().authenticated()
                )

                /*
                 * AUTH PROVIDER
                 */
                .authenticationProvider(
                        authenticationProvider()
                )

                /*
                 * JWT FILTER
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                /*
                 * DISABLE HTML LOGIN
                 */
                .formLogin(form -> form.disable())

                /*
                 * DISABLE BASIC AUTH
                 */
                .httpBasic(basic -> basic.disable())

                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                customUserDetailsService
        );

        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}