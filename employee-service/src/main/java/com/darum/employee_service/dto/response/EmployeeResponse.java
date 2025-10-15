package com.darum.employee_service.dto.response;

import com.darum.employee_service.enums.EmployeeStatus;
import com.darum.employee_service.model.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class EmployeeResponse {
    private String firstName;

    private String lastName;

    private String email;

    private EmployeeStatus status;

    private Department department;

    private LocalDateTime updatedAt;

    // Reference to user who manages this employee (for Manager role)
    private Long managerId;
}
