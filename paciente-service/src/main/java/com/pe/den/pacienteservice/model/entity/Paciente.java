package com.pe.den.pacienteservice.model.entity;

import com.pe.den.pacienteservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paciente", schema = "paciente")
public class Paciente extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paciente")
    @SequenceGenerator(name = "seq_paciente", sequenceName = "paciente.seq_paciente_pk", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false, unique = true)
    private Persona persona;
}