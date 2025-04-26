# 实时录音订单接口文档

## 1. 创建录音订单

### 接口描述
创建一个新的实时录音订单

### 请求信息
- 请求路径：`/api/v1/recording-orders`
- 请求方法：POST
- 请求头：
  ```
  Content-Type: application/json
  Authorization: Bearer {token}
  ```

### 请求参数
```json
{
    "title": "会议录音",           // 录音标题，必填
    "description": "产品讨论会议", // 录音描述，可选
    "expectedDuration": 3600,     // 预计录音时长（秒），必填
    "participantCount": 5,        // 参与人数，必填
    "startTime": "2024-04-26T14:00:00Z", // 预计开始时间，必填
    "settings": {                 // 录音设置，可选
        "quality": "HIGH",        // 音质：LOW/MEDIUM/HIGH
        "format": "MP3",          // 格式：MP3/WAV
        "channels": 2             // 声道数：1/2
    }
}
```

### 响应信息
```json
{
    "code": 200,                  // 状态码
    "message": "创建成功",         // 响应消息
    "data": {
        "orderId": "ORD123456",   // 订单ID
        "status": "PENDING",      // 订单状态：PENDING/RECORDING/COMPLETED/CANCELLED
        "createTime": "2024-04-26T10:30:00Z", // 创建时间
        "roomId": "ROOM123456",   // 录音房间ID
        "accessCode": "123456"    // 房间访问码
    }
}
```

### 错误码
- 400: 请求参数错误
- 401: 未授权
- 500: 服务器内部错误

## 2. 查询录音订单列表

### 接口描述
分页查询用户的录音订单列表

### 请求信息
- 请求路径：`/api/v1/recording-orders`
- 请求方法：GET
- 请求头：
  ```
  Authorization: Bearer {token}
  ```

### 请求参数
- 查询参数：
  ```
  page: 1                    // 页码，从1开始
  size: 10                   // 每页记录数
  status: PENDING           // 订单状态，可选
  startDate: 2024-04-01    // 开始日期，可选
  endDate: 2024-04-26      // 结束日期，可选
  ```

### 响应信息
```json
{
    "code": 200,                  // 状态码
    "message": "查询成功",         // 响应消息
    "data": {
        "total": 100,            // 总记录数
        "pages": 10,             // 总页数
        "current": 1,            // 当前页码
        "records": [             // 订单记录列表
            {
                "orderId": "ORD123456",
                "title": "会议录音",
                "status": "PENDING",
                "createTime": "2024-04-26T10:30:00Z",
                "startTime": "2024-04-26T14:00:00Z",
                "duration": 3600,
                "participantCount": 5
            }
            // ... 更多记录
        ]
    }
}
```

### 错误码
- 400: 请求参数错误
- 401: 未授权
- 500: 服务器内部错误

## 3. 查询录音订单详情

### 接口描述
查询单个录音订单的详细信息

### 请求信息
- 请求路径：`/api/v1/recording-orders/{orderId}`
- 请求方法：GET
- 请求头：
  ```
  Authorization: Bearer {token}
  ```

### 请求参数
无（orderId在路径中）

### 响应信息
```json
{
    "code": 200,                  // 状态码
    "message": "查询成功",         // 响应消息
    "data": {
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
}
```

### 错误码
- 400: 请求参数错误
- 401: 未授权
- 404: 订单不存在
- 500: 服务器内部错误

## 4. 删除录音订单

### 接口描述
删除指定的录音订单

### 请求信息
- 请求路径：`/api/v1/recording-orders/{orderId}`
- 请求方法：DELETE
- 请求头：
  ```
  Authorization: Bearer {token}
  ```

### 请求参数
无（orderId在路径中）

### 响应信息
```json
{
    "code": 200,                  // 状态码
    "message": "删除成功",         // 响应消息
    "data": null
}
```

### 错误码
- 400: 请求参数错误
- 401: 未授权
- 404: 订单不存在
- 403: 无权限删除（非本人订单）
- 500: 服务器内部错误 