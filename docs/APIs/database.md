# 数据库设计文档

## 数据库概述

本项目使用 MySQL 数据库，采用 JPA/Hibernate 作为 ORM 框架。数据库设计遵循以下原则：

1. 使用 InnoDB 存储引擎
2. 所有表使用自增主键
3. 使用 UTF8MB4 字符集
4. 所有表包含创建时间和更新时间字段
5. 使用软删除机制

## 表结构设计

### 用户表 (user)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 描述 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | 自增 | 主键 |
| phone | varchar | 20 | 否 | | 手机号 |
| password | varchar | 100 | 否 | | 密码（BCrypt加密） |
| nickname | varchar | 50 | 是 | null | 昵称 |
| avatar | varchar | 255 | 是 | null | 头像URL |
| status | tinyint | 4 | 否 | 1 | 状态：0-禁用，1-启用 |
| created_at | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除：0-否，1-是 |

索引：
- 主键索引：id
- 唯一索引：phone
- 普通索引：status, created_at

### 用户会话表 (user_session)

| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 描述 |
|--------|------|------|--------|--------|------|
| id | bigint | 20 | 否 | 自增 | 主键 |
| user_id | bigint | 20 | 否 | | 用户ID |
| session_id | varchar | 64 | 否 | | 会话ID |
| token | varchar | 255 | 否 | | JWT令牌 |
| expire_time | datetime | | 否 | | 过期时间 |
| created_at | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| updated_at | datetime | | 否 | CURRENT_TIMESTAMP | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除：0-否，1-是 |

索引：
- 主键索引：id
- 唯一索引：session_id
- 普通索引：user_id, expire_time, created_at

## 表关系

1. 用户表(user)和用户会话表(user_session)是一对多关系
   - 一个用户可以创建多个会话
   - 会话表通过user_id关联到用户表

## 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/xf_service?useUnicode=true&characterEncoding=utf8mb4&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
```

## 数据库维护建议

1. 定期备份
   - 建议每天进行一次全量备份
   - 每小时进行一次增量备份

2. 性能优化
   - 定期分析慢查询日志
   - 优化索引使用
   - 控制单表数据量

3. 安全建议
   - 使用强密码策略
   - 限制数据库访问IP
   - 定期更新数据库版本
   - 启用SSL连接

4. 监控建议
   - 监控数据库连接数
   - 监控慢查询数量
   - 监控磁盘使用情况
   - 设置告警阈值 