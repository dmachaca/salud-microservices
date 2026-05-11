package com.pe.den.personalservice.service;

import com.pe.den.personalservice.model.dto.request.personal.PersonalInputDto;
import com.pe.den.personalservice.model.dto.response.personal.PersonalOutputDto;

import java.util.List;

public interface PersonalSaludService {
    public PersonalOutputDto registrarPersonal(PersonalInputDto dto);
    List<PersonalOutputDto> listarTodo();
}
