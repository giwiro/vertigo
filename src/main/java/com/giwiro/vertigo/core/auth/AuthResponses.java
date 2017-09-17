package com.giwiro.vertigo.core.auth;

public class AuthResponses {

    public static class AuthenticateUserResponse {
        private String message;
        private Boolean success;
        private Object user;
    }
}
