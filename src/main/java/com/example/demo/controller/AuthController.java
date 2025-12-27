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


package com.example.OneToMany.controller;

import com.example.OneToMany.dto.AuthRequest;
import com.example.OneToMany.dto.RegisterRequest;
import com.example.OneToMany.dto.AuthResponse;
import com.example.OneToMany.entity.User;
import com.example.OneToMany.service.UserService;
import com.example.OneToMany.security.JwtTokenProvider;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) {
        User user = userService.registerAndReturnUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );

            User user = userService.findByEmailIgnoreCase(req.getEmail());
            String token = jwtTokenProvider.generateToken(authentication, user);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}
