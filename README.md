# XFService

A Spring Boot application using Spring Boot 3.2.3 and Java 17.

## 项目概述

XFService 是一个基于 Spring Boot 3.2.3 和 Java 17 的微服务应用，提供用户认证和会话管理功能。

## 技术栈

- Spring Boot 3.2.3
- Java 17
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL 8.0
- JWT (JSON Web Token)
- Lombok
- Maven

## 系统架构

### 核心组件

1. 用户认证模块
   - 用户注册
   - 用户登录
   - JWT 令牌管理
   - 会话管理

2. 安全配置
   - Spring Security 集成
   - JWT 认证过滤器
   - 密码加密

3. 数据持久化
   - JPA 实体映射
   - 数据库表结构
   - 数据访问层

### 数据库设计

#### 用户表 (users)
- id: 主键
- phone: 手机号（唯一）
- password: 密码（加密存储）
- nickname: 昵称
- avatar: 头像URL
- status: 状态（1-正常，0-禁用）
- create_time: 创建时间
- update_time: 更新时间

#### 会话表 (user_sessions)
- id: 主键
- session_id: 会话ID（唯一）
- user_id: 用户ID（外键）
- token: JWT令牌
- expire_time: 过期时间
- status: 状态（1-有效，0-无效）
- create_time: 创建时间
- update_time: 更新时间

### API 设计

#### 认证接口

1. 用户注册
```
POST /api/v1/auth/register
Request:
{
    "phone": "string",
    "password": "string",
    "nickname": "string", // 可选
    "avatar": "string"    // 可选
}
Response:
{
    "token": "string",
    "sessionId": "string",
    "userId": "long",
    "phone": "string",
    "nickname": "string",
    "avatar": "string",
    "expireTime": "datetime"
}
```

2. 用户登录
```
POST /api/v1/auth/login
Request:
{
    "phone": "string",
    "password": "string"
}
Response:
{
    "token": "string",
    "sessionId": "string",
    "userId": "long",
    "phone": "string",
    "nickname": "string",
    "avatar": "string",
    "expireTime": "datetime"
}
```

### 安全设计

1. 密码加密
   - 使用 BCrypt 算法加密存储
   - 自动加盐处理

2. JWT 令牌
   - 包含用户标识和过期时间
   - 使用 HS256 算法签名
   - 可配置的过期时间

3. 会话管理
   - 基于 JWT 的无状态会话
   - 支持会话过期和失效
   - 支持并发会话控制

## 环境要求

- Java 17 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.0 或更高版本

## 构建项目

```bash
mvn clean install
```

## 运行应用

```bash
mvn spring-boot:run
```

应用将在 http://localhost:8080 启动

## 配置说明

### 应用配置 (application.yml)

```yaml
server:
  port: 8080

spring:
  application:
    name: XFService
  datasource:
    url: jdbc:mysql://localhost:3306/xfservice?useSSL=false&serverTimezone=UTC
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true

jwt:
  expiration: 3600 # JWT 过期时间（秒）

logging:
  level:
    org.springframework.security: DEBUG
    com.xf.service: DEBUG
```

## 开发指南

1. 代码规范
   - 遵循 Java 编码规范
   - 使用 Lombok 简化代码
   - 保持代码注释完整

2. 测试规范
   - 编写单元测试
   - 编写集成测试
   - 保持测试覆盖率

3. 版本控制
   - 使用 Git 进行版本控制
   - 遵循 Git Flow 工作流
   - 提交信息规范

## 部署说明

1. 数据库准备
   - 创建数据库：xfservice
   - 配置数据库用户权限

2. 应用部署
   - 构建可执行 JAR 包
   - 配置运行环境
   - 启动应用服务

## 维护说明

1. 日志管理
   - 配置日志级别
   - 定期归档日志
   - 监控异常日志

2. 性能优化
   - 数据库索引优化
   - 缓存策略优化
   - JVM 参数调优

3. 安全维护
   - 定期更新依赖
   - 检查安全漏洞
   - 更新安全配置 