package com.darum.auth_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
