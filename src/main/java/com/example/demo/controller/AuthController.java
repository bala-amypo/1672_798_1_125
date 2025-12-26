// package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.entity.User;
// import com.example.demo.service.UserService;
// import com.example.demo.security.JwtTokenProvider;

// import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// public class AuthController {

//     private final AuthenticationManager authenticationManager;
//     private final JwtTokenProvider jwtTokenProvider;
//     private final UserService userService;

//     // 🔥 REQUIRED constructor for tests
//     public AuthController(
//             AuthenticationManager authenticationManager,
//             JwtTokenProvider jwtTokenProvider,
//             UserService userService
//     ) {
//         this.authenticationManager = authenticationManager;
//         this.jwtTokenProvider = jwtTokenProvider;
//         this.userService = userService;
//     }

//     // ===================== REGISTER =====================
//     @PostMapping("/register")
//     public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
//         User user = userService.registerAndReturnUser(req);
//         return ResponseEntity.status(HttpStatus.CREATED).body(user);
//     }

//     // ===================== LOGIN =====================
//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

//         // 🔥 REQUIRED BY TESTS
//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(
//                         req.getEmail(),
//                         req.getPassword()
//                 )
//         );

//         // 🔥 Load user
//         User user = userService.findByEmailIgnoreCase(req.getEmail());

//         // 🔥 Generate token via provider (tests mock this)
//         String token = jwtTokenProvider.generateToken(authentication, user);

//         return ResponseEntity.ok(new AuthResponse(token));
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtTokenProvider;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 🔥 Use only these three dependencies
    public AuthController(
            UserService userService,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
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
        try {
            // 🔥 Load user directly
            User user = userService.findByEmailIgnoreCase(req.getEmail());
            
            // 🔥 Manually check password
            if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            
            // 🔥 Generate token directly
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
            
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            // Return 401 for authentication failures
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}