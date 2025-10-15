package com.darum.employee_service.controller;

import com.darum.employee_service.dto.request.EmployeeRequest;
import com.darum.employee_service.dto.response.EmployeeResponse;
import com.darum.employee_service.model.Employee;
import com.darum.employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

//    @GetMapping
//    public ResponseEntity<Employee> validateEmployee(String token) {
//        logger.info("Fetching all employees");
//        List<EmployeeResponse> employees = employeeService.getEmployees();
//        logger.info("Retrieved {} employees", employees.size());
//        return ResponseEntity.ok(employees);
//    }

    @PreAuthorize(("hasAnyRole('ADMIN')"))
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        logger.info("Creating new employee with request: {}", request);
        EmployeeResponse employeeResponse = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @PreAuthorize(("hasAnyRole('ADMIN')"))
    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable("employeeId") Long employeeId,
            @Valid @RequestBody EmployeeRequest request) {
        logger.info("Updating employee with ID: {} | Request: {}", employeeId, request);
        EmployeeResponse employeeResponse = employeeService.updateEmployee(employeeId, request);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize(("hasAnyRole('ADMIN')"))
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        logger.info("Deleting employee with ID: {}", employeeId);
        employeeService.deleteEmployee(employeeId);
    }

    @PreAuthorize(("hasAnyRole('ADMIN')"))
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeResponse> employees = employeeService.getEmployees();
        logger.info("Retrieved {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize(("hasAnyRole('ADMIN','MANAGER') or #employeeId == authentication.employeeId"))
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        logger.info("Fetching employee with ID: {}", employeeId);
        EmployeeResponse employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }
}
