// package com.example.demo.dto;

// public class AuthRequest {
//     public String email;
//     public String password;
// }

package com.example.demo.dto;

public class AuthRequest {

    private String email;
    private String password;

    // ✅ No-args constructor (IMPORTANT for Spring)
    public AuthRequest() {
    }

    // ✅ Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
