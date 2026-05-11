package com.pe.den.pacienteservice.repository;

import com.pe.den.pacienteservice.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByDni(String dni);
    boolean existsByDni(String dni);
}