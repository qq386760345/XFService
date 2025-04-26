# 用户认证接口文档

## 1. 数据库设计

### 用户表 (users)
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    phone VARCHAR(11) NOT NULL COMMENT '手机号码',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    nickname VARCHAR(20) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 字段说明
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|--------|------|------|--------|--------|------|
| id | BIGINT | - | 否 | - | 用户ID，主键 |
| phone | VARCHAR | 11 | 否 | - | 手机号码，唯一索引 |
| password | VARCHAR | 100 | 否 | - | 密码（加密存储） |
| nickname | VARCHAR | 20 | 是 | NULL | 用户昵称 |
| avatar | VARCHAR | 255 | 是 | NULL | 头像URL |
| status | TINYINT | - | 否 | 1 | 状态：0-禁用，1-正常 |
| create_time | DATETIME | - | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_time | DATETIME | - | 否 | CURRENT_TIMESTAMP | 更新时间 |

## 2. 接口说明

### 2.1 用户注册

#### 请求信息
| 项目 | 内容 |
|------|------|
| 请求路径 | `/api/v1/auth/register` |
| 请求方法 | POST |
| 请求头 | `Content-Type: application/json` |

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| phone | String | 是 | 手机号码，11位 |
| password | String | 是 | 密码，8-20位，必须包含大小写字母和数字 |
| nickname | String | 否 | 昵称，2-20位字符 |
| avatar | String | 否 | 头像URL |

#### 响应信息
| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 响应消息 |
| data | Object | 响应数据 |
| data.userId | String | 用户ID |
| data.phone | String | 手机号码 |
| data.nickname | String | 昵称 |
| data.avatar | String | 头像URL |
| data.createTime | String | 创建时间 |

#### 错误码
| 错误码 | 说明 |
|--------|------|
| 400 | 请求参数错误 |
| 409 | 手机号已被注册 |
| 500 | 服务器内部错误 |

### 2.2 用户登录

#### 请求信息
| 项目 | 内容 |
|------|------|
| 请求路径 | `/api/v1/auth/login` |
| 请求方法 | POST |
| 请求头 | `Content-Type: application/json` |

#### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| phone | String | 是 | 手机号码 |
| password | String | 是 | 密码 |

#### 响应信息
| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 响应消息 |
| data | Object | 响应数据 |
| data.userId | String | 用户ID |
| data.phone | String | 手机号码 |
| data.nickname | String | 昵称 |
| data.avatar | String | 头像URL |
| data.token | String | JWT令牌 |
| data.expiresIn | Integer | 令牌有效期（秒） |

#### 错误码
| 错误码 | 说明 |
|--------|------|
| 400 | 请求参数错误 |
| 401 | 用户名或密码错误 |
| 500 | 服务器内部错误 |

### 2.3 用户注销

#### 请求信息
| 项目 | 内容 |
|------|------|
| 请求路径 | `/api/v1/auth/logout` |
| 请求方法 | POST |
| 请求头 | `Authorization: Bearer {token}` |

#### 请求参数
无

#### 响应信息
| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| message | String | 响应消息 |
| data | Object | 响应数据，为null |

#### 错误码
| 错误码 | 说明 |
|--------|------|
| 401 | 未授权或token已失效 |
| 500 | 服务器内部错误 | 