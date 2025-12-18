package com.example.demo.service.impl;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String register(RegisterRequest req);
    String login(AuthRequest req);
}
