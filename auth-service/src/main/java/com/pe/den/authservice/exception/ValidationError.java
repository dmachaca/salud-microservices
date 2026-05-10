package com.pe.den.authservice.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationError {

    private String field;          // campo (ej: nombreUsuario)
    private Object rejectedValue;  // valor enviado
    private String message;        // mensaje de error
}