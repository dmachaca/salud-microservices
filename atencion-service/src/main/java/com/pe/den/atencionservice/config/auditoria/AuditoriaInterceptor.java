package com.pe.den.atencionservice.config.auditoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class AuditoriaInterceptor {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Este método debe llamarse antes de realizar un save.
     * Establece las variables de sesión en PostgreSQL para los triggers.
     */
    @Transactional
    public void setUsuarioAuditoria(Long usuarioId, String traceId) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            var sql = "SELECT set_config('app.user_id', ?, false), set_config('app.trace_id', ?, false)";
            try (var pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, String.valueOf(usuarioId));
                pstmt.setString(2, traceId != null ? traceId : "sistema-default");
                pstmt.execute();
                log.debug("Auditoría configurada en DB: Usuario {}, Trace {}", usuarioId, traceId);
            }
        });
    }
}