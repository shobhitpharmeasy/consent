package com.pharmeasy.consent.service;

import com.pharmeasy.consent.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeByEmail(String employeeEmail);

    EmployeeDto updateEmployee(String employeeEmail, EmployeeDto employeeDto);

    String deleteEmployee(String employeeEmail);

    String updatePassword(String employeeEmail, String newPassword);

    Boolean isAuthorized(String employeeEmail, String token);
}
