# API 接口文档

## 1. 认证接口

### 1.1 用户注册

#### 请求信息
- 请求路径：`/api/auth/register`
- 请求方法：POST
- 请求头：`Content-Type: application/json`

#### 请求参数
```json
{
    "phone": "手机号",
    "password": "密码",
    "nickname": "昵称"
}
```

#### 响应信息
- 成功响应：HTTP 200
- 失败响应：HTTP 400/409/500

### 1.2 用户登录

#### 请求信息
- 请求路径：`/api/auth/login`
- 请求方法：POST
- 请求头：`Content-Type: application/json`

#### 请求参数
```json
{
    "phone": "手机号",
    "password": "密码"
}
```

#### 响应信息
```json
{
    "message": "登录成功"
}
```

### 1.3 用户登出

#### 请求信息
- 请求路径：`/api/auth/logout`
- 请求方法：POST
- 请求头：`Authorization: Bearer {token}`

#### 响应信息
- 成功响应：HTTP 200

## 2. 录音订单接口

### 2.1 创建录音订单

#### 请求信息
- 请求路径：`/api/v1/recording-orders`
- 请求方法：POST
- 请求头：
  ```
  Content-Type: application/json
  Authorization: Bearer {token}
  ```

#### 请求参数
```json
{
    "title": "会议录音",
    "description": "产品讨论会议",
    "expectedDuration": 3600,
    "participantCount": 5,
    "startTime": "2024-04-26T14:00:00Z",
    "settings": {
        "quality": "HIGH",
        "format": "MP3",
        "channels": 2
    }
}
```

#### 响应信息
```json
{
    "orderId": "ORD123456",
    "status": "PENDING",
    "createTime": "2024-04-26T10:30:00Z",
    "roomId": "ROOM123456",
    "accessCode": "123456"
}
```

### 2.2 查询录音订单列表

#### 请求信息
- 请求路径：`/api/v1/recording-orders`
- 请求方法：GET
- 请求头：`Authorization: Bearer {token}`

#### 请求参数
- 查询参数：
  ```
  page: 页码（从1开始）
  size: 每页记录数
  status: 订单状态（可选）
  startDate: 开始日期（可选）
  endDate: 结束日期（可选）
  ```

#### 响应信息
```json
{
    "total": 100,
    "pages": 10,
    "current": 1,
    "records": [
        {
            "orderId": "ORD123456",
            "title": "会议录音",
            "status": "PENDING",
            "createTime": "2024-04-26T10:30:00Z",
            "startTime": "2024-04-26T14:00:00Z",
            "duration": 3600,
            "participantCount": 5
        }
    ]
}
```

### 2.3 查询录音订单详情

#### 请求信息
- 请求路径：`/api/v1/recording-orders/{orderId}`
- 请求方法：GET
- 请求头：`Authorization: Bearer {token}`

#### 响应信息
```json
{
    "orderId": "ORD123456",
    "title": "会议录音",
    "description": "产品讨论会议",
    "status": "RECORDING",
    "createTime": "2024-04-26T10:30:00Z",
    "startTime": "2024-04-26T14:00:00Z",
    "actualStartTime": "2024-04-26T14:02:00Z",
    "duration": 3600,
    "currentDuration": 1800,
    "participantCount": 5,
    "currentParticipants": 3,
    "roomId": "ROOM123456",
    "accessCode": "123456",
    "settings": {
        "quality": "HIGH",
        "format": "MP3",
        "channels": 2
    }
}
```

### 2.4 删除录音订单

#### 请求信息
- 请求路径：`/api/v1/recording-orders/{orderId}`
- 请求方法：DELETE
- 请求头：`Authorization: Bearer {token}`

#### 响应信息
- 成功响应：HTTP 200

## 3. 错误码说明

| 错误码 | 描述 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 未授权或token已失效 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 409 | 资源冲突 |
| 500 | 服务器内部错误 |

## 4. 安全说明

1. 所有请求（除了登录和注册）都需要在 Header 中携带 JWT 令牌：
   ```
   Authorization: Bearer {token}
   ```

2. JWT 令牌过期时间为 1 小时（可配置）

3. 密码在传输和存储时都经过加密处理

4. 建议使用 HTTPS 进行通信 