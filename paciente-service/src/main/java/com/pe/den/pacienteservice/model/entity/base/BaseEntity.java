package com.pe.den.pacienteservice.model.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity
        extends AuditableEntity {

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public void prePersistBase() {

        if (activo == null) {
            activo = true;
        }
    }
}