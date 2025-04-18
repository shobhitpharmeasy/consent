package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.dto.EmployeeDto;
import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.entity.Service;
import com.pharmeasy.consent.mapper.EmployeeMapper;
import com.pharmeasy.consent.repository.EmployeeRepository;
import com.pharmeasy.consent.utils.HashUtils;
import com.pharmeasy.consent.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class EmployeeServiceImpl
    implements com.pharmeasy.consent.service.EmployeeService
{

    private final EmployeeMapper employeeMapper;

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto createEmployee(final EmployeeDto employeeDto) {
        log.info("Creating employee: {}", employeeDto.email());

        final String randomPassword = RandomUtils.generateRandomPassword();
        final String hashPassword = HashUtils.hash(randomPassword);

        final Employee employee = Employee
                                      .builder()
                                      .email(employeeDto.email())
                                      .firstName(employeeDto.firstName())
                                      .middleName(employeeDto.middleName())
                                      .lastName(employeeDto.lastName())
                                      .passwordHash(hashPassword)
                                      .role(employeeDto.role())
                                      .build()
            ;
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            log.info("Employee saved: {}", savedEmployee.getEmail());

            return employeeMapper.toDto(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException(STR."Employee already exists: \{employeeDto.email()}");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployee() {
        log.info("Fetching all employees");
        return employeeRepository
                   .findAll()
                   .stream()
                   .map(employeeMapper::toDto)
                   .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeByEmail(final String employeeEmail) {
        log.info("Fetching employee by email: {}", employeeEmail);

        final Employee employee =
            employeeRepository.findById(employeeEmail).orElseThrow(() -> {
                log.warn("Employee not found: {}", employeeEmail);
                return new RuntimeException(STR."Email not found: \{employeeEmail}");
            });

        return employeeMapper.toDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(
        final String employeeEmail,
        final EmployeeDto employeeDto
                                     )
    {
        log.info("Updating employee: {}", employeeEmail);

        final Employee existingEmployee = employeeRepository.findById(
            employeeEmail).orElseThrow(() -> {
            log.warn("Employee not found for update: {}", employeeEmail);
            return new RuntimeException(STR."Email not found: \{employeeEmail}");
        });

        if (employeeDto.firstName() != null) {
            existingEmployee.setFirstName(employeeDto.firstName());
        }
        if (employeeDto.middleName() != null) {
            existingEmployee.setMiddleName(employeeDto.middleName());
        }
        if (employeeDto.lastName() != null) {
            existingEmployee.setLastName(employeeDto.lastName());
        }
        if (employeeDto.email() != null && !employeeDto.email().equals(
            employeeEmail))
        {
            existingEmployee.setEmail(employeeDto.email());
        }

        final Employee updatedEmployee = employeeRepository.save(
            existingEmployee);
        log.info("Employee updated: {}", updatedEmployee.getEmail());

        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public String deleteEmployee(final String employeeEmail) {
        log.info("Deleting employee: {}", employeeEmail);

        final Employee employee =
            employeeRepository.findById(employeeEmail).orElseThrow(() -> {
                log.warn(
                    "Attempted to delete non-existent employee: {}",
                    employeeEmail
                        );
                return new RuntimeException(STR."Email not found: \{employeeEmail}");
            });

        for (Service service : employee.getOwnedServices()) {
            service.setDeleted(true);
        }

        employeeRepository.delete(employee);
        log.info("Deleted employee: {}", employeeEmail);

        return MessageFormat.format(
            "Deleted employee with Email: {0}",
            employeeEmail
                                   );
    }

    @Override
    @Transactional
    public String updatePassword(
        final String employeeEmail,
        final String newPassword
                                )
    {
        log.info("Updating password for employee: {}", employeeEmail);

        final Employee employee =
            employeeRepository.findById(employeeEmail).orElseThrow(() -> {
                log.warn(
                    "Password update failed - employee not found: {}",
                    employeeEmail
                        );
                return new RuntimeException(STR."Email not found: \{employeeEmail}");
            });

        employee.setPasswordHash(HashUtils.hash(newPassword));
        employeeRepository.save(employee);

        log.info(
            "Password updated successfully for employee: {}",
            employeeEmail
                );
        return "Password updated successfully";
    }
}
