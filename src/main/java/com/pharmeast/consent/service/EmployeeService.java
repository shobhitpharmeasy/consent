package com.pharmeast.consent.service;


public interface EmployeeService {

    com.pharmeast.consent.dto.EmployeeDto createEmployee(
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    );

    java.util.List< com.pharmeast.consent.dto.EmployeeDto > getAllEmployee();

    com.pharmeast.consent.dto.EmployeeDto getEmployeeByEmail(
        String employeeEmail
    );

    com.pharmeast.consent.dto.EmployeeDto updateEmployee(
        String employeeEmail,
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    );

    String deleteEmployee( String employeeEmail );

    String updatePassword( String employeeEmail, String newPassword );

    Boolean isAuthorized( String employeeEmail, String token );
}
