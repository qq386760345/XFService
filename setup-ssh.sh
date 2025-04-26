#!/bin/bash

# 远程服务器信息
REMOTE_HOST="1.95.60.219"
REMOTE_USER="root"
REMOTE_PASS="Tjpt@2020"

# 检查是否已经存在 SSH 密钥
if [ ! -f ~/.ssh/id_rsa ]; then
    echo "Generating SSH key pair..."
    # 生成 SSH 密钥对，使用空密码短语
    ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa -N ""
fi

# 创建 expect 脚本
cat > setup-ssh.exp << EOF
#!/usr/bin/expect -f
set timeout 30
set host "$REMOTE_HOST"
set user "$REMOTE_USER"
set password "$REMOTE_PASS"

# 确保 .ssh 目录存在并设置正确的权限
spawn ssh -o StrictHostKeyChecking=no \$user@\$host "mkdir -p ~/.ssh && chmod 700 ~/.ssh"
expect {
    "yes/no" { send "yes\r"; exp_continue }
    "password:" { send "\$password\r" }
}
expect eof

# 复制公钥到远程服务器
spawn ssh-copy-id -o StrictHostKeyChecking=no -i /Users/hasanhe/.ssh/id_rsa.pub \$user@\$host
expect {
    "yes/no" { send "yes\r"; exp_continue }
    "password:" { send "\$password\r" }
}
expect eof
EOF

# 设置 expect 脚本权限
chmod +x setup-ssh.exp

# 执行 expect 脚本
echo "Setting up SSH trust..."
./setup-ssh.exp

# 清理临时文件
rm setup-ssh.exp

# 测试 SSH 连接
echo "Testing SSH connection..."
ssh -o BatchMode=yes -o ConnectTimeout=5 $REMOTE_USER@$REMOTE_HOST echo "SSH connection successful!"

# 配置 SSH 配置文件
echo "Configuring SSH config..."
mkdir -p ~/.ssh
cat >> ~/.ssh/config << EOF
Host $REMOTE_HOST
    HostName $REMOTE_HOST
    User $REMOTE_USER
    IdentityFile ~/.ssh/id_rsa
    StrictHostKeyChecking no
EOF

# 设置正确的权限
chmod 600 ~/.ssh/config

echo "SSH trust setup completed!" 