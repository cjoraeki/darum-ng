package com.darum.api_gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalAuthFilter implements GlobalFilter {

    private final WebClient.Builder webClientBuilder;

    private static final String AUTH_SERVICE_URL = "http://auth-service/api/auth/validate";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // Skip authentication for auth endpoints
        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String jwtToken = token.substring(7); // remove "Bearer "

        return webClientBuilder.build()
                .get()
                .uri(AUTH_SERVICE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(Boolean.class)
                .flatMap(valid -> {
                    if (Boolean.TRUE.equals(valid)) {
                        return chain.filter(exchange);
                    } else {
                        return unauthorized(exchange, "Invalid token");
                    }
                })
                .onErrorResume(error -> {
                    log.error("Auth service error: {}", error.getMessage());
                    return unauthorized(exchange, "Authentication failed");
                });
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.warn("Unauthorized request: {}", message);
        return response.setComplete();
    }
}

//@Component
//@RequiredArgsConstructor
//public class GlobalAuthFilter implements GlobalFilter {
//
//    private final AuthClient authClient;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (token == null || !authClient.validateToken(token)) {
//            throw new RuntimeException("Invalid or missing authentication token: "+ HttpStatus.UNAUTHORIZED);
//        }
//
//        return chain.filter(exchange);
//    }
//}