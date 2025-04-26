package com.xf.service.service;

import com.xf.service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(User user);
    User findByPhone(String phone);
    void updateUser(User user);
} 