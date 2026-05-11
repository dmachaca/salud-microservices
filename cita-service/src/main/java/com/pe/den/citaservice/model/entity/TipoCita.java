package com.pe.den.citaservice.model.entity;

import com.pe.den.citaservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "tipo_cita", schema = "cita")
public class TipoCita extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_cita")
    @SequenceGenerator(name = "seq_tipo_cita", sequenceName = "cita.seq_tipo_cita_pk", allocationSize = 1)
    private Long id;

    @Column(unique = true, length = 30)
    private String nombre;
}