package com.pe.den.authservice.repository;


import com.pe.den.authservice.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByJtiAndRevocadoFalse(String jti);

    @Query("""
        SELECT r FROM RefreshToken r
        WHERE r.jti = :jti
          AND r.revocado = false
          AND r.expiracion > :now
    """)
    Optional<RefreshToken> findValidToken(
            @Param("jti") String jti,
            @Param("now") OffsetDateTime now
    );

    @Modifying
    @Query("""
        UPDATE RefreshToken r
        SET r.revocado = true
        WHERE r.usuario.id = :userId
          AND r.revocado = false
    """)
    int revokeAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("""
        UPDATE RefreshToken r
        SET r.revocado = true
        WHERE r.jti = :jti
    """)
    int revokeByJti(@Param("jti") String jti);


    @Modifying
    @Query("""
        DELETE FROM RefreshToken r
        WHERE r.expiracion < :now
           OR r.revocado = true
    """)
    int deleteExpiredOrRevoked(@Param("now") OffsetDateTime now);
}