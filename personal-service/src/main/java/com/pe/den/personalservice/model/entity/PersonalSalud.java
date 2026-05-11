package com.pe.den.personalservice.model.entity;

import com.pe.den.personalservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "personal_salud", schema = "personal")
@Getter @Setter
public class PersonalSalud extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_gen")
    @SequenceGenerator(name = "personal_gen", sequenceName = "seq_personal_salud_pk", schema = "personal", allocationSize = 1)
    private Long id;

    @Column(name = "persona_id", nullable = false)
    private Long personaId; // Referencia lógica al microservicio de personas/pacientes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id")
    private Establecimiento establecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_personal_id")
    private TipoPersonal tipoPersonal;

    @Column(unique = true)
    private String colegiatura;

    // Relación con especialidades (Many-to-Many profesional)
    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonalEspecialidad> especialidades = new HashSet<>();
}