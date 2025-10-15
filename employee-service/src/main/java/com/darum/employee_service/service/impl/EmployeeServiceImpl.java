package com.darum.employee_service.service.impl;

import com.darum.employee_service.controller.EmployeeController;
import com.darum.employee_service.dto.request.EmployeeRequest;
import com.darum.employee_service.dto.response.EmployeeResponse;
import com.darum.employee_service.enums.EmployeeStatus;
import com.darum.employee_service.enums.Role;
import com.darum.employee_service.exception.ResourceNotFoundException;
import com.darum.employee_service.model.Employee;
import com.darum.employee_service.repository.EmployeeRepository;
import com.darum.employee_service.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        logger.debug("Creating employee with request: {}", request);

        Employee employee = mapToEntity(request);
        Employee savedEmployee = employeeRepository.save(employee);

        logger.info("Employee created successfully with ID: {}", savedEmployee.getEmployeeId());
        return mapToResponse(savedEmployee);
    }

    @Override
    public Employee validateToken(String token) {
        return null;
    }


    @Override
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request) {
        logger.debug("Updating employee with ID: {}", employeeId);

        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        updateEntityFromRequest(existingEmployee, request);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        logger.info("Employee updated successfully with ID: {}", updatedEmployee.getEmployeeId());
        return mapToResponse(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        logger.debug("Deleting employee with ID: {}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        employeeRepository.delete(employee);
        logger.info("Employee deleted successfully with ID: {}", employeeId);
    }

    @Override
    public List<EmployeeResponse> getEmployees() {
        logger.debug("Fetching all employees");

        return employeeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeById(Long employeeId) {
        logger.debug("Fetching employee with ID: {}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        return mapToResponse(employee);
    }

    private Employee mapToEntity(EmployeeRequest request) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .roles(new HashSet<>(List.of(Role.EMPLOYEE.toString())))
                .status(EmployeeStatus.ACTIVE)
                .department(request.getDepartment())
                .build();
    }


    private void updateEntityFromRequest(Employee employee, EmployeeRequest request) {
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getEmail() != null) employee.setEmail(request.getEmail());
        if (request.getDepartment() != null) employee.setDepartment(request.getDepartment());
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .build();
    }
}
