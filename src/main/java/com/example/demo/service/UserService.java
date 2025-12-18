package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;

public interface UserService {
    String register(RegisterRequest req);
    String login(AuthRequest req);
}
