package com.xf.service.controller;

import com.xf.service.dto.*;
import com.xf.service.entity.User;
import com.xf.service.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        logger.info("收到注册请求: phone={}", request.getPhone());
        try {
            User user = new User();
            user.setPhone(request.getPhone());
            user.setPassword(request.getPassword());
            user.setNickname(request.getNickname());
            userService.register(user);
            logger.info("用户注册成功: phone={}", request.getPhone());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("用户注册失败: phone={}, error={}", request.getPhone(), e.getMessage(), e);
            return ResponseEntity.badRequest().body("注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        logger.info("收到登录请求: phone={}", request.getPhone());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("用户登录成功: phone={}", request.getPhone());
            
            return ResponseEntity.ok(new LoginResponse("登录成功"));
        } catch (AuthenticationException e) {
            logger.warn("用户登录失败: phone={}, error={}", request.getPhone(), e.getMessage());
            return ResponseEntity.badRequest().body(new LoginResponse("登录失败: " + e.getMessage()));
        } catch (Exception e) {
            logger.error("登录过程发生异常: phone={}, error={}", request.getPhone(), e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new LoginResponse("服务器内部错误"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("用户登出: phone={}", auth.getName());
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.ok().build();
    }
} 