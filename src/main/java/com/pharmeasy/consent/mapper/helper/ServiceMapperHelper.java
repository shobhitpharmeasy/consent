package com.pharmeasy.consent.mapper.helper;

import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceMapperHelper {

    private final EmployeeRepository employeeRepository;

    public String employeeToString(Employee employee) {
        return employee != null ? employee.getEmail() : null;
    }

    public Employee stringToEmployee(String email) {
        if (email == null) return null;
        return employeeRepository.findById(email).orElse(Employee.builder().email(email).build());
    }
}
