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
        name = "rol",
        schema = "auth",
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "rol_nombre_key",
                        columnNames = "nombre"
                )
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class Rol extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_rol"
    )
    @SequenceGenerator(
            name = "seq_rol",
            sequenceName = "auth.seq_rol_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Integer id;

    @Column(
            name = "nombre",
            nullable = false,
            length = 30
    )
    private String nombre;

    @Column(
            name = "descripcion",
            length = 150
    )
    private String descripcion;

    @OneToMany(
            mappedBy = "rol",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles;
}