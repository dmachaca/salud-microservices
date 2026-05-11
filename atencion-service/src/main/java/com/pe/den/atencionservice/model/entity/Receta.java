package com.pe.den.atencionservice.model.entity;

import com.pe.den.atencionservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "receta", schema = "atencion")
public class Receta extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_receta")
    @SequenceGenerator(name = "seq_receta", sequenceName = "atencion.seq_receta_pk", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id")
    private Atencion atencion;

    @Column(length = 150)
    private String medicamento;

    @Column(length = 100)
    private String dosis;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(length = 100)
    private String duracion;
}