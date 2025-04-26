# 认证接口文档

## 用户注册

注册新用户并返回认证信息。

### 请求

```http
POST /api/v1/auth/register
Content-Type: application/json
```

### 请求参数

| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| phone | string | 是 | 用户手机号 |
| password | string | 是 | 用户密码 |
| nickname | string | 否 | 用户昵称 |
| avatar | string | 否 | 用户头像URL |

### 请求示例

```json
{
    "phone": "13800138000",
    "password": "password123",
    "nickname": "测试用户",
    "avatar": "https://example.com/avatar.jpg"
}
```

### 响应

| 参数名 | 类型 | 描述 |
|--------|------|------|
| token | string | JWT认证令牌 |
| sessionId | string | 会话ID |
| userId | long | 用户ID |
| phone | string | 用户手机号 |
| nickname | string | 用户昵称 |
| avatar | string | 用户头像URL |
| expireTime | datetime | 令牌过期时间 |

### 响应示例

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "sessionId": "a1b2c3d4-e5f6-7890",
    "userId": 1,
    "phone": "13800138000",
    "nickname": "测试用户",
    "avatar": "https://example.com/avatar.jpg",
    "expireTime": "2024-04-26T20:00:00"
}
```

### 错误码

| 错误码 | 描述 |
|--------|------|
| 400 | 请求参数错误 |
| 409 | 手机号已存在 |
| 500 | 服务器内部错误 |

## 用户登录

用户登录并获取认证信息。

### 请求

```http
POST /api/v1/auth/login
Content-Type: application/json
```

### 请求参数

| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| phone | string | 是 | 用户手机号 |
| password | string | 是 | 用户密码 |

### 请求示例

```json
{
    "phone": "13800138000",
    "password": "password123"
}
```

### 响应

| 参数名 | 类型 | 描述 |
|--------|------|------|
| token | string | JWT认证令牌 |
| sessionId | string | 会话ID |
| userId | long | 用户ID |
| phone | string | 用户手机号 |
| nickname | string | 用户昵称 |
| avatar | string | 用户头像URL |
| expireTime | datetime | 令牌过期时间 |

### 响应示例

```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "sessionId": "a1b2c3d4-e5f6-7890",
    "userId": 1,
    "phone": "13800138000",
    "nickname": "测试用户",
    "avatar": "https://example.com/avatar.jpg",
    "expireTime": "2024-04-26T20:00:00"
}
```

### 错误码

| 错误码 | 描述 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 用户名或密码错误 |
| 403 | 用户已被禁用 |
| 500 | 服务器内部错误 |

## 安全说明

1. 所有请求都需要在 Header 中携带 JWT 令牌（除了登录和注册接口）：
   ```
   Authorization: Bearer <token>
   ```

2. JWT 令牌过期时间为 1 小时（可配置）

3. 密码在传输和存储时都经过加密处理

4. 建议使用 HTTPS 进行通信 