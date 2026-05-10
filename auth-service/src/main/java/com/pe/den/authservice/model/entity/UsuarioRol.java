package com.pe.den.authservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.pe.den.authservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "usuario_rol",
        schema = "auth",
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_usuario_rol",
                        columnNames = {
                                "usuario_id",
                                "rol_id"
                        }
                )
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class UsuarioRol extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_usuario_rol"
    )
    @SequenceGenerator(
            name = "seq_usuario_rol",
            sequenceName = "auth.seq_usuario_rol_pk",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    /*
     * USUARIO
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "usuario_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_usuario_rol_usuario"
            )
    )
    private Usuario usuario;

    /*
     * ROL
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rol_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_usuario_rol_rol"
            )
    )
    private Rol rol;
}