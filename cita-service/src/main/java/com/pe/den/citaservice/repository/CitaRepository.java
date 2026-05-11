package com.pe.den.citaservice.repository;

import com.pe.den.citaservice.model.entity.Cita;
import com.pe.den.citaservice.model.entity.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Para validar que el médico no tenga otra cita a la misma hora
    boolean existsByPersonalIdAndFechaHoraAndActivoTrue(Long personalId, ZonedDateTime fechaHora);

    // Para buscar estados por nombre (maestros)
    @org.springframework.data.jpa.repository.Query("SELECT e FROM EstadoCita e WHERE e.nombre = :nombre")
    Optional<EstadoCita> findEstadoByNombre(String nombre);

    Page<Cita> findByPacienteIdAndActivoTrue(Long pacienteId, Pageable pageable);
}