package com.pe.den.authservice.model.entity.base;

import com.pe.den.authservice.config.AuditoriaContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private OffsetDateTime fechaActualizacion;

    @Column(name = "creado_por_id", updatable = false)
    private Long creadoPorId;

    @Column(name = "actualizado_por_id")
    private Long actualizadoPorId;

    private static final ZoneOffset ZONA = ZoneOffset.UTC;

    @PrePersist
    public void prePersistAuditable() {

        if (fechaCreacion == null) {
            fechaCreacion = OffsetDateTime.now(ZONA);
        }

        Long userId = AuditoriaContext.getCurrentUserId();

        if (userId != null) {
            creadoPorId = userId;
        }
    }

    @PreUpdate
    public void preUpdateAuditable() {

        fechaActualizacion = OffsetDateTime.now(ZONA);

        Long userId = AuditoriaContext.getCurrentUserId();

        if (userId != null) {
            actualizadoPorId = userId;
        }
    }
}