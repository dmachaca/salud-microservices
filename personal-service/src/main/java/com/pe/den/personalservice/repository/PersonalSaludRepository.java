package com.pe.den.personalservice.repository;

import com.pe.den.personalservice.model.entity.PersonalSalud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalSaludRepository extends JpaRepository<PersonalSalud, Long> {
    Optional<PersonalSalud> findByPersonaId(Long personaId);
    boolean existsByColegiatura(String colegiatura);
}