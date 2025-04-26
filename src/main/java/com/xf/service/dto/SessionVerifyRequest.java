package com.xf.service.dto;

import jakarta.validation.constraints.NotBlank;

public class SessionVerifyRequest {
    @NotBlank(message = "会话ID不能为空")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
} 