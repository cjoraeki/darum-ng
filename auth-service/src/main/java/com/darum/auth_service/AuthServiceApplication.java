package com.darum.auth_service;

import com.darum.auth_service.enums.Role;
import com.darum.auth_service.model.User;
import com.darum.auth_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class AuthServiceApplication {
	private final static Logger logger = LoggerFactory.getLogger(AuthServiceApplication.class);
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@PostConstruct
	public String createAdmin(){

		User user = User.builder()
				.username("cjoraeki")
				.id(Long.valueOf(UUID.randomUUID().toString()))
				.email("cjoraeki@darum.com")
				.password(passwordEncoder.encode("1234"))
				.roles(new HashSet<>(List.of(Role.ADMIN.toString())))
				.build();
		logger.info("Admin registration: {}", user);
		userRepository.save(user);
		return "ADMIN registered successfully";
	}
}
