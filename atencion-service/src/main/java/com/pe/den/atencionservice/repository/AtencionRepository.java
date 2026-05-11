package com.pe.den.atencionservice.repository;

import com.pe.den.atencionservice.model.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    // Para validar que una cita no tenga dos atenciones
    boolean existsByCitaIdAndActivoTrue(Long citaId);

    Optional<Atencion> findByCitaId(Long citaId);
}