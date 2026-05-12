package com.pe.den.authservice.service;

import com.pe.den.authservice.model.dto.request.usuario.UsuarioInputDto;
import com.pe.den.authservice.model.dto.response.usuario.UsuarioOutputDto;

public interface UsuarioService {
    UsuarioOutputDto registrarPaciente(UsuarioInputDto request);
}
