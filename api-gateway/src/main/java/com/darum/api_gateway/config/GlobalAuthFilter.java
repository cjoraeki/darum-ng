package com.darum.api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GlobalAuthFilter implements GlobalFilter {

    private final AuthClient authClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (token == null || !authClient.validateToken(token)) {
            throw new RuntimeException("Invalid or missing authentication token: "+ HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

}
