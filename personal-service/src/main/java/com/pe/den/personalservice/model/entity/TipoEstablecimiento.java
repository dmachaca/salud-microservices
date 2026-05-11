package com.pe.den.personalservice.model.entity;

import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_establecimiento", schema = "personal")
@Getter
@Setter
public class TipoEstablecimiento extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_est_gen")
    @SequenceGenerator(name = "tipo_est_gen", sequenceName = "seq_tipo_establecimiento_pk", schema = "personal", allocationSize = 1)
    private Long id;

    private String nombre;
}