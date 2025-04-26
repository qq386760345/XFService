#!/bin/bash

# 远程服务器信息
REMOTE_HOST="1.95.60.219"
REMOTE_USER="root"
REMOTE_DIR="/usr/server/XFService"
JAR_NAME="xf-service.jar"
SSH_KEY="~/.ssh/xf_service_key"

# 创建远程目录
ssh -i $SSH_KEY $REMOTE_USER@$REMOTE_HOST "mkdir -p $REMOTE_DIR"

# 推送jar包
scp -i $SSH_KEY target/$JAR_NAME $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/

# 推送启动和停止脚本
scp -i $SSH_KEY start.sh $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/
scp -i $SSH_KEY stop.sh $REMOTE_USER@$REMOTE_HOST:$REMOTE_DIR/

# 设置脚本权限
ssh -i $SSH_KEY $REMOTE_USER@$REMOTE_HOST "chmod +x $REMOTE_DIR/start.sh $REMOTE_DIR/stop.sh"

# 启动服务
ssh -i $SSH_KEY $REMOTE_USER@$REMOTE_HOST "cd $REMOTE_DIR && ./start.sh"

echo "Deployment and service startup completed!" 