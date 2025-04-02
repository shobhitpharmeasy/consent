package com.pharmeast.consent.mapper;

import com.pharmeast.consent.dto.EmployeeDto;
import com.pharmeast.consent.entity.Employee;

public class EmployeeMapper {
    private EmployeeMapper() {
        // Private constructor to prevent instantiation
    }

    public static EmployeeDto maptoEmployeeDto(Employee employee) {
        return new EmployeeDto(
            employee.getId(),
            employee.getFirstName(),
            employee.getMiddleName(),
            employee.getLastName(),
            employee.getEmail()
        );
    }

    public static Employee maptoEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        if (employeeDto.getId() != null) {
            employee.setId(employeeDto.getId());
        }
        employee.setFirstName(employeeDto.getFirstName());
        employee.setMiddleName(employeeDto.getMiddleName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        return employee;
    }
}
