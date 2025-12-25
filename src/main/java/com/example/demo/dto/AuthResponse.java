package com.example.demo.dto;

public class AuthResponse {

    private String token;

    // Required by tests
    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
