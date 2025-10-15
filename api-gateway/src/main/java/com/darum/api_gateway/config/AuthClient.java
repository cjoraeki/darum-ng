package com.darum.api_gateway.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="auth-service")
public interface AuthClient {

    @GetMapping("/api/auth/validate")
    Boolean validateToken(@RequestHeader("Authorization") String token);
}
