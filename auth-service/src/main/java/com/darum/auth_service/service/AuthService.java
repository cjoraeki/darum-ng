package com.darum.auth_service.service;

import com.darum.auth_service.dto.request.LoginRequest;
import com.darum.auth_service.dto.request.RegisterRequest;
import com.darum.auth_service.model.User;

public interface AuthService {
    User registerUser(RegisterRequest request);

    User authenticate(LoginRequest request);

    void validateToken(String token);

    String generateToken(String username);
}
