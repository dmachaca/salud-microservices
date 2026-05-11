package com.pe.den.pacienteservice.repository;

import com.pe.den.pacienteservice.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p JOIN FETCH p.persona WHERE p.persona.dni = :dni")
    Optional<Paciente> findByDniWithPersona(@Param("dni") String dni);
    boolean existsByIdAndActivoTrue(Long pacienteId);
}