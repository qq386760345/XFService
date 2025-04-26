#!/bin/bash

# 服务名称
SERVICE_NAME="xf-service"
JAR_NAME="xf-service.jar"

# 检查服务是否在运行
if ! pgrep -f $JAR_NAME > /dev/null; then
    echo "Service is not running!"
    exit 1
fi

# 停止服务
pkill -f $JAR_NAME

# 等待服务停止
sleep 5

# 检查服务是否成功停止
if ! pgrep -f $JAR_NAME > /dev/null; then
    echo "Service stopped successfully!"
else
    echo "Failed to stop service!"
    exit 1
fi 