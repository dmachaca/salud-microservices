package com.pe.den.personalservice.model.entity;
import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personal_especialidad", schema = "personal")
@Getter @Setter
public class PersonalEspecialidad extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "per_esp_gen")
    @SequenceGenerator(name = "per_esp_gen", sequenceName = "seq_personal_especialidad_pk", schema = "personal", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id")
    private PersonalSalud personal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
}