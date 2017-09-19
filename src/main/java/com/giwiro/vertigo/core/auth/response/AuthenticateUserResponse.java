package com.giwiro.vertigo.core.auth.response;

import com.giwiro.vertigo.core.models.CoreUser;

public class AuthenticateUserResponse {
    private String message;
    private Boolean success;
    private User user;

    public AuthenticateUserResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public AuthenticateUserResponse(String message, Boolean success, CoreUser user) {
        this.message = message;
        this.success = success;
        this.user = new User(user);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private String username;
        private String email;
        private int glassPts;
        private int level;
        private int nextLvlPts;
        private int paperPts;
        private int plasticPts;
        private int progress;
        private long registeredAt;
        private int totalPts;

        public User(CoreUser user) {
            this.username = user.getUsername();
            this.email = user.getEmail();
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getGlassPts() {
            return glassPts;
        }

        public void setGlassPts(int glassPts) {
            this.glassPts = glassPts;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getNextLvlPts() {
            return nextLvlPts;
        }

        public void setNextLvlPts(int nextLvlPts) {
            this.nextLvlPts = nextLvlPts;
        }

        public int getPaperPts() {
            return paperPts;
        }

        public void setPaperPts(int paperPts) {
            this.paperPts = paperPts;
        }

        public int getPlasticPts() {
            return plasticPts;
        }

        public void setPlasticPts(int plasticPts) {
            this.plasticPts = plasticPts;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public long getRegisteredAt() {
            return registeredAt;
        }

        public void setRegisteredAt(long registeredAt) {
            this.registeredAt = registeredAt;
        }

        public int getTotalPts() {
            return totalPts;
        }

        public void setTotalPts(int totalPts) {
            this.totalPts = totalPts;
        }
    }
}
