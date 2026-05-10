package com.pe.den.citaservice.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
