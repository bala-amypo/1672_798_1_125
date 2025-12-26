// package com.example.demo.service.impl;

// import com.example.demo.service.UserService;
// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.entity.User;
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.repository.UserRepository;
// import com.example.demo.security.JwtTokenProvider;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// @Service
// public class UserServiceImpl implements UserService {

//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtTokenProvider jwtTokenProvider;

//     // 🔥 REQUIRED BY JUNIT TESTS
//     public UserServiceImpl(
//             UserRepository userRepository,
//             PasswordEncoder passwordEncoder
//     ) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.jwtTokenProvider = null; // tests don't use JWT here
//     }

//     // 🔥 REQUIRED BY SPRING BOOT
//     @Autowired
//     public UserServiceImpl(
//             UserRepository userRepository,
//             PasswordEncoder passwordEncoder,
//             JwtTokenProvider jwtTokenProvider
//     ) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.jwtTokenProvider = jwtTokenProvider;
//     }

//     // ================= REGISTER (STRING) =================
//     @Override
//     public String register(RegisterRequest req) {

//         if (userRepository.findByEmailIgnoreCase(req.getEmail()).isPresent()) {
//             throw new BadRequestException("Email already in use");
//         }

//         User user = new User();
//         user.setFullName(req.getFullName());
//         user.setEmail(req.getEmail());
//         user.setPassword(passwordEncoder.encode(req.getPassword()));
//         user.setRole(req.getRole() != null ? req.getRole() : "MANAGER");

//         userRepository.save(user);
//         return "User registered successfully";
//     }

//     // ================= REGISTER (USER) =================
//     @Override
//     public User registerAndReturnUser(RegisterRequest req) {

//         if (userRepository.findByEmailIgnoreCase(req.getEmail()).isPresent()) {
//             throw new BadRequestException("Email already in use");
//         }

//         User user = new User();
//         user.setFullName(req.getFullName());
//         user.setEmail(req.getEmail());
//         user.setPassword(passwordEncoder.encode(req.getPassword()));
//         user.setRole(req.getRole() != null ? req.getRole() : "MANAGER");

//         return userRepository.save(user);
//     }

//     // ================= LOGIN =================
//     @Override
//     public String login(AuthRequest req) {

//         User user = userRepository
//                 .findByEmailIgnoreCase(req.getEmail())
//                 .orElseThrow(() -> new BadRequestException("Invalid credentials"));

//         if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//             throw new BadRequestException("Invalid credentials");
//         }

//         if (jwtTokenProvider == null) {
//             return "DUMMY_TOKEN";
//         }

//         return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
//     }

//     // 🔥 REQUIRED BY TESTS
//     @Override
//     public User findByEmailIgnoreCase(String email) {
//         return userRepository.findByEmailIgnoreCase(email)
//                 .orElseThrow(() -> new BadRequestException("Invalid credentials"));
//     }
// }



package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // Single constructor with all required dependencies
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String register(RegisterRequest req) {
        if (userRepository.findByEmailIgnoreCase(req.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole() != null ? req.getRole() : "MANAGER");

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public User registerAndReturnUser(RegisterRequest req) {
        if (userRepository.findByEmailIgnoreCase(req.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole() != null ? req.getRole() : "MANAGER");

        return userRepository.save(user);
    }

    @Override
    public String login(AuthRequest req) {
        // First verify user exists and password matches
        User user = userRepository
                .findByEmailIgnoreCase(req.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        // Use authentication manager to authenticate
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
            )
        );

        // Generate token using the authenticated user
        return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadRequestException("User not found"));
    }
}