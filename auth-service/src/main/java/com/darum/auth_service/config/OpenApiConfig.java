package com.darum.auth_service.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the Employee Management microservice.
 * This provides metadata and customizes Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Darum Employee Management API")
                        .description("REST API for managing employees and departments in the HR system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Darum HR System")
                                .email("support@darum.com")
                                .url("https://darum.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}

