package com.pe.den.personalservice.model.entity;

import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_personal", schema = "personal")
@Getter
@Setter
public class TipoPersonal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_pers_gen")
    @SequenceGenerator(name = "tipo_pers_gen", sequenceName = "seq_tipo_personal_pk", schema = "personal", allocationSize = 1)
    private Long id;

    private String nombre;
}