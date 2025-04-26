package com.xf.service;

import com.xf.service.controller.AuthController;
import com.xf.service.dto.LoginRequest;
import com.xf.service.dto.RegisterRequest;
import com.xf.service.entity.User;
import com.xf.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterAndLogin() {
        // 测试注册
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138000");
        registerRequest.setPassword("123456");
        registerRequest.setNickname("测试用户");

        ResponseEntity<?> registerResponse = authController.register(registerRequest);
        assertEquals(200, registerResponse.getStatusCode().value());

        // 验证用户是否已创建
        User user = userService.findByPhone("13800138000");
        assertNotNull(user);
        assertTrue(passwordEncoder.matches("123456", user.getPassword()));
        assertEquals("测试用户", user.getNickname());

        // 测试登录
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138000");
        loginRequest.setPassword("123456");

        ResponseEntity<?> loginResponse = authController.login(loginRequest);
        assertEquals(200, loginResponse.getStatusCode().value());
    }

    @Test
    public void testRegisterDuplicatePhone() {
        // 先注册一个用户
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138001");
        registerRequest.setPassword("123456");
        registerRequest.setNickname("测试用户1");
        authController.register(registerRequest);

        // 尝试用相同的手机号注册
        RegisterRequest duplicateRequest = new RegisterRequest();
        duplicateRequest.setPhone("13800138001");
        duplicateRequest.setPassword("654321");
        duplicateRequest.setNickname("测试用户2");

        ResponseEntity<?> response = authController.register(duplicateRequest);
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void testLoginWithWrongPassword() {
        // 先注册一个用户
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138002");
        registerRequest.setPassword("123456");
        registerRequest.setNickname("测试用户3");
        authController.register(registerRequest);

        // 使用错误密码登录
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138002");
        loginRequest.setPassword("wrongpassword");

        ResponseEntity<?> response = authController.login(loginRequest);
        assertEquals(400, response.getStatusCode().value());
    }
} 