#!/bin/bash

# 服务名称
SERVICE_NAME="xf-service"
JAR_NAME="xf-service.jar"
LOG_FILE="app.log"

# 检查服务是否已经在运行
if pgrep -f $JAR_NAME > /dev/null; then
    echo "Service is already running!"
    exit 1
fi

# 启动服务
nohup java -jar $JAR_NAME > $LOG_FILE 2>&1 &

# 等待服务启动
sleep 5

# 检查服务是否成功启动
if pgrep -f $JAR_NAME > /dev/null; then
    echo "Service started successfully!"
else
    echo "Failed to start service!"
    exit 1
fi 