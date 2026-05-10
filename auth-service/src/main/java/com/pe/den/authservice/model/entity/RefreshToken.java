package com.pe.den.authservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.pe.den.authservice.model.entity.base.AuditableEntity;
import com.pe.den.authservice.model.entity.base.BaseEntity;
import jakarta.persistence.*;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "refresh_token",
        schema = "auth",
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_refresh_token_hash",
                        columnNames = "token_hash"
                )
        }
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({
        "hibernateLazyInitializer",
        "handler"
})
public class RefreshToken extends AuditableEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_refresh_token"
    )
    @SequenceGenerator(
            name = "seq_refresh_token",
            sequenceName = "auth.seq_refresh_token_pk",
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
                    name = "fk_refresh_token_usuario"
            )
    )
    @JsonIgnore
    private Usuario usuario;

    /*
     * TOKEN
     */
    @JsonIgnore
    @Column(
            name = "token_hash",
            nullable = false
    )
    private String tokenHash;

    @Column(
            name = "jti",
            nullable = false,
            length = 100
    )
    private String jti;

    @Column(
            name = "expiracion",
            nullable = false
    )
    private OffsetDateTime expiracion;

    @Column(name = "revocado")
    private Boolean revocado = false;

    @Column(name = "reutilizado")
    private Boolean reutilizado = false;

    @Column(
            name = "ip_creacion",
            length = 45
    )
    private String ipCreacion;

    @Column(name = "user_agent")
    private String userAgent;
}