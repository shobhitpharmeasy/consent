package com.pharmeast.consent.service.impl;

@org.springframework.stereotype.Service
@lombok.RequiredArgsConstructor
@org.springframework.validation.annotation.Validated
public class EmployeeServiceImpl
    implements com.pharmeast.consent.service.EmployeeService {

    private static final java.util.regex.Pattern pattern
        = java.util.regex.Pattern.compile( "^[^@]+@pharmeasy\\.in$" );

    private final com.pharmeast.consent.mapper.EmployeeMapper employeeMapper;
    private final com.pharmeast.consent.utils.JwtUtils jwtUtils;
    private final com.pharmeast.consent.repository.EmployeeRepository employeeRepository;
    // constructor injection

    @Override
    public com.pharmeast.consent.dto.EmployeeDto createEmployee(
        @jakarta.validation.Valid com.pharmeast.consent.dto.EmployeeDto employeeDto
    ) {
        // Logic to create an employee
        if ( employeeRepository.existsById( employeeDto.getEmail() ) ) {
            throw new com.pharmeast.consent.exception.MissingParameterException(
                "Employee with ID " + employeeDto.getEmail() + " already exists." );
        }

        final String randomPassword
            = com.pharmeast.consent.utils.RandomUtils.generateRandomPassword();
        final String hashPassword = com.pharmeast.consent.utils.HashUtils.hash(
            randomPassword );

        employeeDto.setPasswordHash( hashPassword );

        com.pharmeast.consent.entity.Employee employee = employeeMapper.toEntity(
            employeeDto );
        employee.setEmail( employeeDto.getEmail() );
        employee.setPasswordHash( hashPassword );
        employee.setFirstName( employeeDto.getFirstName() );
        employee.setMiddleName( employeeDto.getMiddleName() );
        employee.setLastName( employeeDto.getLastName() );
//        employee.setRole( employeeDto.getRole() );
        employee.setOwnedServices( new java.util.ArrayList<>() );
//        employee.setAccessibleServices( new java.util.ArrayList<>() );

        com.pharmeast.consent.entity.Employee savedEmployee = employeeRepository.save(
            employee );

        return employeeMapper.toDto( savedEmployee );
    }

    @Override
    public java.util.List< com.pharmeast.consent.dto.EmployeeDto > getAllEmployee() {
        // Logic to get all employees
        return employeeRepository.findAll().stream().map( employeeMapper::toDto )
                                 .toList();
        // .toArray(EmployeeDto[]::new);
    }

    @Override
    public com.pharmeast.consent.dto.EmployeeDto getEmployeeByEmail(
        String employeeEmail
    ) {
        // Logic to get an employee by ID
        com.pharmeast.consent.entity.Employee employee = employeeRepository.findById(
            employeeEmail ).orElseThrow(
            () -> new com.pharmeast.consent.exception.MissingEmailException(
                employeeEmail ) );
        return employeeMapper.toDto( employee );
    }

    @Override
    public com.pharmeast.consent.dto.EmployeeDto updateEmployee(
        String employeeEmail,
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    ) {
        // Logic to update an employee
        com.pharmeast.consent.entity.Employee existingEmployee = employeeRepository
            .findById( employeeEmail ).orElseThrow(
                () -> new com.pharmeast.consent.exception.MissingEmailException(
                    employeeEmail ) );

        if ( employeeDto.getFirstName() != null ) {
            existingEmployee.setFirstName( employeeDto.getFirstName() );
        }

        if ( employeeDto.getMiddleName() != null ) {
            existingEmployee.setMiddleName( employeeDto.getMiddleName() );
        }
        if ( employeeDto.getLastName() != null ) {
            existingEmployee.setLastName( employeeDto.getLastName() );
        }
        if ( employeeDto.getEmail() != null ) {
            // use regex to check if email ends with @pharmeasy.in and has greater than one character
            if ( !pattern.matcher( employeeDto.getEmail() ).matches() ) {
                throw new com.pharmeast.consent.exception.WrongEmailException(
                    "Invalid email format" );
            }
            existingEmployee.setEmail( employeeDto.getEmail() );
        }

        com.pharmeast.consent.entity.Employee updatedEmployee = employeeRepository.save(
            existingEmployee );
        return employeeMapper.toDto( updatedEmployee );
    }

    @Override
    public String deleteEmployee( String employeeEmail ) {
        // Logic to delete an employee
        com.pharmeast.consent.entity.Employee employee = employeeRepository.findById(
            employeeEmail ).orElseThrow(
            () -> new com.pharmeast.consent.exception.MissingEmailException(
                employeeEmail ) );
        employeeRepository.delete( employee );
        return java.text.MessageFormat.format(
            "Deleted employee with Email: {0}", employeeEmail );
    }

    @Override
    public String updatePassword( String employeeEmail, String newPassword ) {

        com.pharmeast.consent.entity.Employee employee = employeeRepository.findById(
            employeeEmail ).orElseThrow(
            () -> new com.pharmeast.consent.exception.MissingEmailException(
                employeeEmail ) );

        employee.setPasswordHash(
            com.pharmeast.consent.utils.HashUtils.hash( newPassword ) );
        employeeRepository.save( employee );
        return "Password updated successfully";
    }

    @Override
    public Boolean isAuthorized( String employeeEmail, String token ) {

        String usernameFromToken = jwtUtils.extractUsername(
            token.replace( "Bearer ", "" ) );
        return usernameFromToken.equals( employeeEmail );
    }
}
