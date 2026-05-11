package com.pe.den.atencionservice.model.entity;

import com.pe.den.atencionservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "atencion", schema = "atencion")
public class Atencion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atencion")
    @SequenceGenerator(name = "seq_atencion", sequenceName = "atencion.seq_atencion_pk", allocationSize = 1)
    private Long id;

    @Column(name = "cita_id", nullable = false, unique = true)
    private Long citaId;

    @Column(name = "diagnostico_cie10", length = 10)
    private String diagnosticoCie10;

    @Column(name = "diagnostico_descripcion", columnDefinition = "TEXT")
    private String diagnosticoDescripcion;

    @Column(name = "plan_tratamiento", columnDefinition = "TEXT")
    private String planTratamiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    // Relación con las recetas
    @OneToMany(mappedBy = "atencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas;
}