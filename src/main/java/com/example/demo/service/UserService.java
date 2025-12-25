package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;

public interface UserService {

    // ✅ keep existing methods (DO NOT REMOVE)
    String register(RegisterRequest req);
    String login(AuthRequest req);

    // 🔥 REQUIRED FOR TESTS
    User registerAndReturnUser(RegisterRequest req);
}
