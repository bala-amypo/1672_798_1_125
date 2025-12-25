package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    // 🔥 SINGLE constructor (works for tests + runtime)
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        User user = userService.registerAndReturnUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        // 🔥 Delegate to service (handles validation + JWT safely)
        String token = userService.login(req);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
