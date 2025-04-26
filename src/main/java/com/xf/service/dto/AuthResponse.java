package com.xf.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String sessionId;
    private Long userId;
    private String phone;
    private String nickname;
    private String avatar;
    private LocalDateTime expireTime;
} 