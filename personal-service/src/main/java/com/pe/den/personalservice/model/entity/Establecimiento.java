package com.pe.den.personalservice.model.entity;

import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "establecimiento", schema = "personal")
@Getter
@Setter
public class Establecimiento extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "est_gen")
    @SequenceGenerator(name = "est_gen", sequenceName = "seq_establecimiento_pk", schema = "personal", allocationSize = 1)
    private Long id;

    private String nombre;
    private String direccion;
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_establecimiento_id")
    private TipoEstablecimiento tipoEstablecimiento;
}