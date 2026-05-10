package com.pe.den.authservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.pe.den.authservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(
        name = "usuario",
        schema = "auth",
        uniqueConstraints = {@UniqueConstraint(
                        name = "usuario_nombre_usuario_key",
                        columnNames = "nombre_usuario"
                ),
                @UniqueConstraint(
                        name = "usuario_correo_key",
                        columnNames = "correo"
                )
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_usuario"
    )
    @SequenceGenerator(
            name = "seq_usuario",
            sequenceName = "auth.seq_usuario_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    /*
     * PERSONA
     * REFERENCIA A MICROSERVICIO PERSONA
     */
    @Column(name = "persona_id", nullable = false)
    private Long personaId;

    /*
     * LOGIN
     */
    @Column(
            name = "nombre_usuario",
            nullable = false,
            length = 50
    )
    private String nombreUsuario;

    @JsonIgnore
    @Column(
            name = "clave_hash",
            nullable = false
    )
    private String claveHash;

    @Column(
            name = "correo",
            nullable = false,
            length = 100
    )
    private String correo;

    /*
     * ROLES
     */
    @OneToMany(
            mappedBy = "usuario",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles;
}