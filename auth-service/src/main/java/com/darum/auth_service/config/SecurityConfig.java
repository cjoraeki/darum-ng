package com.darum.auth_service.config;

import com.darum.auth_service.AuthServiceApplication;
import com.darum.auth_service.enums.Role;
import com.darum.auth_service.model.User;
import com.darum.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final static Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public String createAdmin(){
//
//        User user = User.builder()
//                .username("cjoraeki")
//                .id(1L)
//                .email("cjoraeki@darum.com")
//                .password(passwordEncoder.encode("1234"))
//                .roles(new HashSet<>(List.of(Role.ADMIN.toString())))
//                .build();
//        logger.info("Admin registration: {}", user);
//        userRepository.save(user);
//        return "ADMIN registered successfully";
//    }
}
