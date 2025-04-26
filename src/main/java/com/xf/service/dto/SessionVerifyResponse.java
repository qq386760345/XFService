package com.xf.service.dto;

public class SessionVerifyResponse {
    private boolean valid;
    private Long userId;
    private String phone;
    private String nickname;
    private String avatar;

    public SessionVerifyResponse() {
    }

    public SessionVerifyResponse(boolean valid, Long userId, String phone, String nickname, String avatar) {
        this.valid = valid;
        this.userId = userId;
        this.phone = phone;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static SessionVerifyResponseBuilder builder() {
        return new SessionVerifyResponseBuilder();
    }

    public static class SessionVerifyResponseBuilder {
        private boolean valid;
        private Long userId;
        private String phone;
        private String nickname;
        private String avatar;

        SessionVerifyResponseBuilder() {
        }

        public SessionVerifyResponseBuilder valid(boolean valid) {
            this.valid = valid;
            return this;
        }

        public SessionVerifyResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public SessionVerifyResponseBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public SessionVerifyResponseBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public SessionVerifyResponseBuilder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public SessionVerifyResponse build() {
            return new SessionVerifyResponse(valid, userId, phone, nickname, avatar);
        }
    }
} 