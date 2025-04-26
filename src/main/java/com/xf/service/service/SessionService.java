package com.xf.service.service;

import com.xf.service.entity.User;
import com.xf.service.entity.UserSession;

public interface SessionService {
    UserSession createSession(User user, String deviceInfo, String ipAddress);
    UserSession verifySession(String sessionId);
    void invalidateSession(String sessionId);
    void cleanupExpiredSessions();
} 