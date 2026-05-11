package com.pe.den.citaservice.model.entity;

import com.pe.den.citaservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "cita", schema = "cita",
        uniqueConstraints = @UniqueConstraint(columnNames = {"personal_id", "fecha_hora"}))
public class Cita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cita")
    @SequenceGenerator(name = "seq_cita", sequenceName = "cita.seq_cita_pk", allocationSize = 1)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "personal_id", nullable = false)
    private Long personalId;

    @Column(name = "establecimiento_id", nullable = false)
    private Long establecimientoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_cita_id")
    private EstadoCita estadoCita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cita_id")
    private TipoCita tipoCita;

    @Column(name = "fecha_hora", nullable = false)
    private ZonedDateTime fechaHora;

    private String motivo;

    private String observacion;
}