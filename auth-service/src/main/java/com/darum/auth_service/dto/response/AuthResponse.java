package com.darum.auth_service.dto.response;

import java.util.Set;

public class AuthResponse {
    public String token;
    public String username;
    public Set<String> roles;

    public AuthResponse(String token, String username, Set<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
