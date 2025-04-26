package com.xf.service.service;

import com.xf.service.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    /**
     * 注册新用户
     * @param user 用户信息
     * @throws RuntimeException 如果手机号已存在
     */
    void register(User user);

    /**
     * 根据手机号查找用户
     * @param phone 手机号
     * @return 用户信息
     */
    User findByPhone(String phone);

    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUser(User user);
} 