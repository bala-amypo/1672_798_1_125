package com.example.demo.service.impl;
import com.example.demo.service.UserService; 
import org.springframework.stereotype.Service;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 🔴 EXACT constructor order
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new BadRequestException("Email already in use");
        }

        User user = new User();
        user.setFullName(req.fullName);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));
        if (req.role != null) {
            user.setRole(req.role);
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(AuthRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
    }
}
