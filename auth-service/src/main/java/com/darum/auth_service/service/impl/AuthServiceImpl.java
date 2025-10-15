package com.darum.auth_service.service.impl;

import com.darum.auth_service.dto.request.LoginRequest;
import com.darum.auth_service.dto.request.RegisterRequest;
import com.darum.auth_service.enums.Role;
import com.darum.auth_service.model.User;
import com.darum.auth_service.repository.UserRepository;
import com.darum.auth_service.service.AuthService;
import com.darum.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    protected Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public User registerUser(RegisterRequest request) {
        User user = new User();
        Optional<User> userExist = userRepository.findByEmail(request.getEmail());

        if (userExist.isPresent()) {
            logger.info("User exists: {}", userExist);
            throw new RuntimeException("Admin already exists. Go to Login");
        } else {
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setRoles(new HashSet<>(List.of(Role.EMPLOYEE.toString())));
            userRepository.save(user);
            logger.info("Employee registered: {}", user);
        }
        return user;
    }

    @Override
    public User authenticate(LoginRequest request) {
        User user = new User();
        Optional<User> userExist = userRepository.findByEmail(request.getUsername());
        return null;
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
