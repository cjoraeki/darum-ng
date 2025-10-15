package com.darum.employee_service.service;

import com.darum.employee_service.dto.request.EmployeeRequest;
import com.darum.employee_service.dto.response.EmployeeResponse;
import com.darum.employee_service.model.Employee;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    Employee validateToken(String token);

    EmployeeResponse updateEmployee(Long employeeId, EmployeeRequest request);

    void deleteEmployee(Long employeeId);

    List<EmployeeResponse> getEmployees();

    EmployeeResponse getEmployeeById(Long employeeId);
}
