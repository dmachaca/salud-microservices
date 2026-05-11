package com.pe.den.citaservice.model.entity;

import com.pe.den.citaservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "estado_cita", schema = "cita")
public class EstadoCita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estado_cita")
    @SequenceGenerator(name = "seq_estado_cita", sequenceName = "cita.seq_estado_cita_pk", allocationSize = 1)
    private Long id;

    @Column(unique = true, length = 20)
    private String nombre;
}