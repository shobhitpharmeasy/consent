package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.entity.Service;
import com.pharmeasy.consent.mapper.EmployeeMapper;
import com.pharmeasy.consent.repository.EmployeeRepository;
import com.pharmeasy.consent.utils.HashUtils;
import com.pharmeasy.consent.utils.JwtUtils;
import com.pharmeasy.consent.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class EmployeeServiceImpl
    implements com.pharmeasy.consent.service.EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        if (employeeRepository.existsById(employeeDto.getEmail())) {
            throw new RuntimeException("Employee with ID " + employeeDto.getEmail() + " already exists.");
        }

        String randomPassword = RandomUtils.generateRandomPassword();
        String hashPassword = HashUtils.hash(randomPassword);

        Employee employee = Employee.builder().email(employeeDto.getEmail()).firstName(employeeDto.getFirstName())
                                    .middleName(employeeDto.getMiddleName()).lastName(employeeDto.getLastName())
                                    .passwordHash(hashPassword).role(employeeDto.getRole()).build();

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployee() {

        return employeeRepository.findAll().stream().map(employeeMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeByEmail(String employeeEmail) {

        Employee employee = employeeRepository.findById(employeeEmail).orElseThrow(
            () -> new RuntimeException("Email not found: " + employeeEmail));

        enrichEmployeeWithServiceAccess(employee);

        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(String employeeEmail, EmployeeDto employeeDto) {

        Employee existingEmployee = employeeRepository.findById(employeeEmail).orElseThrow(
            () -> new RuntimeException("Email not found: " + employeeEmail));

        if (employeeDto.getFirstName() != null) {
            existingEmployee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getMiddleName() != null) {
            existingEmployee.setMiddleName(employeeDto.getMiddleName());
        }
        if (employeeDto.getLastName() != null) {
            existingEmployee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getEmail() != null && !employeeDto.getEmail().equals(employeeEmail)) {
            existingEmployee.setEmail(employeeDto.getEmail());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public String deleteEmployee(String employeeEmail) {

        Employee employee = employeeRepository.findById(employeeEmail).orElseThrow(
            () -> new RuntimeException("Email not found: " + employeeEmail));

        employeeRepository.delete(employee);
        return MessageFormat.format("Deleted employee with Email: {0}", employeeEmail);
    }

    @Override
    @Transactional
    public String updatePassword(String employeeEmail, String newPassword) {

        Employee employee = employeeRepository.findById(employeeEmail).orElseThrow(
            () -> new RuntimeException("Email not found: " + employeeEmail));

        employee.setPasswordHash(HashUtils.hash(newPassword));
        employeeRepository.save(employee);
        return "Password updated successfully";
    }

    @Override
    public Boolean isAuthorized(String employeeEmail, String token) {

        String usernameFromToken = jwtUtils.extractUsername(token.replace("Bearer ", ""));
        return usernameFromToken.equals(employeeEmail);
    }

    private void enrichEmployeeWithServiceAccess(Employee employee) {

        List<Service> requested = employeeRepository.findRequestedServicesByEmployeeEmail(employee.getEmail());
        List<Service> accessible = employeeRepository.findAccessibleServicesByEmployeeEmail(employee.getEmail());

        employee.setRequestedServices(requested);
        employee.setAccessibleServices(accessible);
    }
}
