package com.pe.den.pacienteservice.model.entity;

import com.pe.den.pacienteservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona", schema = "paciente")
public class Persona extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_persona")
    @SequenceGenerator(name = "seq_persona", sequenceName = "paciente.seq_persona_pk", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "genero", columnDefinition = "char(1)", length = 1)
    private String genero;

    @Column(length = 20)
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String direccion;
}