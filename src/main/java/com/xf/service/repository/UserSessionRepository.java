package com.xf.service.repository;

import com.xf.service.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    UserSession findBySessionId(String sessionId);
    
    List<UserSession> findByUserIdAndStatus(Long userId, Integer status);
    
    @Modifying
    @Query("UPDATE UserSession s SET s.status = 0 WHERE s.sessionId = :sessionId")
    void invalidateSession(@Param("sessionId") String sessionId);
    
    @Modifying
    @Query("UPDATE UserSession s SET s.status = 0 WHERE s.expireTime < :now")
    void invalidateExpiredSessions(@Param("now") LocalDateTime now);
} 