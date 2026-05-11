package com.pe.den.personalservice.model.entity;

import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "especialidad", schema = "personal")
@Getter @Setter
public class Especialidad extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "esp_gen")
    @SequenceGenerator(name = "esp_gen", sequenceName = "seq_especialidad_pk", schema = "personal", allocationSize = 1)
    private Long id;

    private String nombre;
}