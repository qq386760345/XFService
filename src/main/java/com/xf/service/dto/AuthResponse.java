package com.xf.service.dto;

import java.time.LocalDateTime;

public class AuthResponse {
    private String token;
    private String sessionId;
    private Long userId;
    private String phone;
    private String nickname;
    private String avatar;
    private LocalDateTime expireTime;

    public AuthResponse() {
    }

    public AuthResponse(String token, String sessionId, Long userId, String phone, String nickname, String avatar, LocalDateTime expireTime) {
        this.token = token;
        this.sessionId = sessionId;
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
        this.avatar = avatar;
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
} 