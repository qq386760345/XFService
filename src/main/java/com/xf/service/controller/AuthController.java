package com.xf.service.controller;

import com.xf.service.dto.*;
import com.xf.service.entity.User;
import com.xf.service.entity.UserSession;
import com.xf.service.service.SessionService;
import com.xf.service.service.UserService;
import com.xf.service.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        user.setAvatar(request.getAvatar());

        User savedUser = userService.register(user);
        
        AuthResponse response = AuthResponse.builder()
                .userId(savedUser.getId())
                .phone(savedUser.getPhone())
                .nickname(savedUser.getNickname())
                .avatar(savedUser.getAvatar())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userService.findByPhone(request.getPhone());
        
        UserSession session = sessionService.createSession(
            user,
            httpRequest.getHeader("User-Agent"),
            httpRequest.getRemoteAddr()
        );

        AuthResponse response = AuthResponse.builder()
                .userId(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .sessionId(session.getSessionId())
                .expireTime(session.getExpireTime())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-session")
    public ResponseEntity<?> verifySession(@Valid @RequestBody SessionVerifyRequest request) {
        UserSession session = sessionService.verifySession(request.getSessionId());
        if (session == null) {
            return ResponseEntity.status(401).body(new ErrorResponse("会话无效或已过期"));
        }

        User user = session.getUser();
        SessionVerifyResponse response = SessionVerifyResponse.builder()
                .valid(true)
                .userId(user.getId())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody SessionLogoutRequest request) {
        sessionService.invalidateSession(request.getSessionId());
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
} 