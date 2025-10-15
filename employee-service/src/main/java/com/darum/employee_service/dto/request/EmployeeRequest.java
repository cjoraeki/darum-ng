package com.darum.employee_service.dto.request;

import com.darum.employee_service.model.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequest {
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 26)
    private String firstName;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 26)
    private String username;

    @Size(min = 2, max = 26)
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Size(min = 5, max = 26)
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Schema(defaultValue = "IT")
    @NotBlank(message = "Department cannot be blank")
    @Size(min = 2, max = 26)
    private Department department;
}
