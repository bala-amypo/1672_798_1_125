package com.example.demo.dto;

public class AuthRequest {

    private String email;
    private String password;

    // No-args constructor (important for Spring & tests)
    public AuthRequest() {
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
