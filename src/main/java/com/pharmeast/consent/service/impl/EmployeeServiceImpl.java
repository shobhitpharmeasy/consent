package com.pharmeast.consent.service.impl;

import com.pharmeast.consent.dto.EmployeeDto;
import com.pharmeast.consent.entity.Employee;
import com.pharmeast.consent.exception.MissingIdException;
import com.pharmeast.consent.exception.MissingParameterException;
import com.pharmeast.consent.mapper.EmployeeMapper;
import com.pharmeast.consent.repository.EmployeeRepository;
import com.pharmeast.consent.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final String MISSING_PARAMETER_MESSAGE = "Missing required fields: ";
    private static final String MISSING_ID_MESSAGE = "{0}";

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        // Logic to create an employee


        // check if all required fields are present
        // if (employeeDto.getFirstName() == null || employeeDto.getLastName() == null || employeeDto.getEmail() == null) {
        //     throw new MissingParameterException(
        //         MessageFormat.format("Missing required fields: firstName={0}, lastName={1}, email={2}",
        //             employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail())
        //     );
        // }

        List<String> missingFields = new ArrayList<>();

        if (employeeDto.getFirstName() == null) missingFields.add("firstName");
        if (employeeDto.getLastName() == null) missingFields.add("lastName");
        if (employeeDto.getEmail() == null) missingFields.add("email");
        if (employeeDto.getMiddleName() == null) employeeDto.setMiddleName("");

        if (!missingFields.isEmpty()) {
            throw new MissingParameterException(MISSING_PARAMETER_MESSAGE + String.join(", ", missingFields));
        }

        Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.maptoEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        // Logic to get all employees
        return employeeRepository.findAll().stream().
            map(EmployeeMapper::maptoEmployeeDto).toList();
        // .toArray(EmployeeDto[]::new);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        // Logic to get an employee by ID
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new MissingIdException(MessageFormat.format(MISSING_ID_MESSAGE, employeeId)));
        return EmployeeMapper.maptoEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        // Logic to update an employee
        Employee existingEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new MissingIdException(MessageFormat.format(MISSING_ID_MESSAGE, employeeId)));

        if (employeeDto.getFirstName() != null) {
            existingEmployee.setFirstName(employeeDto.getFirstName());
        }

        if (employeeDto.getMiddleName() != null) {
            existingEmployee.setMiddleName(employeeDto.getMiddleName());
        }
        if (employeeDto.getLastName() != null) {
            existingEmployee.setLastName(employeeDto.getLastName());
        }
        if (employeeDto.getEmail() != null) {
            existingEmployee.setEmail(employeeDto.getEmail());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.maptoEmployeeDto(updatedEmployee);
    }

    @Override
    public String deleteEmployee(Long employeeId) {
        // Logic to delete an employee
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new MissingIdException(MessageFormat.format(MISSING_ID_MESSAGE, employeeId)));
        employeeRepository.delete(employee);
        return MessageFormat.format("Deleted employee with ID: {0}", employeeId);
    }
}
