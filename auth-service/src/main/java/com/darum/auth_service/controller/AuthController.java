package com.darum.auth_service.controller;

import com.darum.auth_service.dto.request.LoginRequest;
import com.darum.auth_service.dto.request.RegisterRequest;
import com.darum.auth_service.dto.response.AuthResponse;
import com.darum.auth_service.model.User;
import com.darum.auth_service.service.AuthService;
import com.darum.auth_service.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/validate")
    Boolean validateToken(@RequestHeader("token") String token) {
        authService.validateToken(token);
        return true;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        logger.info("LoginRequest body: {}", request);

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = authService.generateToken(request.getUsername());
            return ResponseEntity.ok(token);
        } else {
            throw new RuntimeException("Access denied.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("RegisterRequest body: {}", request);
        User user = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
