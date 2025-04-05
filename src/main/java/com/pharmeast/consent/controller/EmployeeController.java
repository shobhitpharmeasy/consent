package com.pharmeast.consent.controller;

@lombok.RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/employees")
@io.swagger.v3.oas.annotations.tags.Tag(
    name = "Employee", description = "Employee management APIs"
)
@org.springframework.validation.annotation.Validated
public class EmployeeController {

    private final com.pharmeast.consent.service.EmployeeService employeeService;

    @org.springframework.web.bind.annotation.PostMapping
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(summary = "Create a new employee")
    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.EmployeeDto > createEmployee(
        @jakarta.validation.Valid @org.springframework.web.bind.annotation.RequestBody
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    ) {

        com.pharmeast.consent.dto.EmployeeDto createdEmployee
            = employeeService.createEmployee( employeeDto );
        java.net.URI location
            = org.springframework.web.servlet.support.ServletUriComponentsBuilder
            .fromCurrentRequest().path( "/{id}" ).buildAndExpand(
                createdEmployee.getEmail() ).toUri();
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return org.springframework.http.ResponseEntity.created( location ).body(
            createdEmployee );
    }

    @org.springframework.web.bind.annotation.GetMapping
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all employees")
    public org.springframework.http.ResponseEntity< java.util.List< com.pharmeast.consent.dto.EmployeeDto > > getAllEmployee() {

        java.util.List< com.pharmeast.consent.dto.EmployeeDto > employees
            = employeeService.getAllEmployee();
        return org.springframework.http.ResponseEntity.ok( employees );
    }

    @org.springframework.web.bind.annotation.GetMapping("/{employeeEmail}")
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(summary = "Get employee by email")
    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.EmployeeDto > getEmployeeById(
        @org.springframework.web.bind.annotation.PathVariable String employeeEmail
    ) {

        com.pharmeast.consent.dto.EmployeeDto employee
            = employeeService.getEmployeeByEmail( employeeEmail );
        return org.springframework.http.ResponseEntity.ok( employee );
    }

    @org.springframework.web.bind.annotation.PutMapping("/{employeeEmail}")
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Update employee by email"
    )
    public org.springframework.http.ResponseEntity< com.pharmeast.consent.dto.EmployeeDto > updateEmployee(
        @org.springframework.web.bind.annotation.PathVariable String employeeEmail,
        @org.springframework.web.bind.annotation.RequestBody
        com.pharmeast.consent.dto.EmployeeDto employeeDto
    ) {

        com.pharmeast.consent.dto.EmployeeDto updatedEmployee
            = employeeService.updateEmployee( employeeEmail, employeeDto );
        return org.springframework.http.ResponseEntity.ok( updatedEmployee );
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{employeeEmail}")
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasRole('ADMIN')"
    )
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Delete employee by email"
    )
    public org.springframework.http.ResponseEntity< String > deleteEmployee(
        @org.springframework.web.bind.annotation.PathVariable String employeeEmail
    ) {

        String response = employeeService.deleteEmployee( employeeEmail );
        return org.springframework.http.ResponseEntity.ok( response );
    }

    @org.springframework.web.bind.annotation.PutMapping(
        "/{employeeEmail}/password"
    )
    @org.springframework.security.access.prepost.PreAuthorize(
        "hasAnyRole('ADMIN', 'USER')"
    )
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Update employee password"
    )
    public org.springframework.http.ResponseEntity< String > updatePassword(
        @org.springframework.web.bind.annotation.PathVariable String employeeEmail,
        @org.springframework.web.bind.annotation.RequestBody
        java.util.Map< String, String > passwordRequest,
        @org.springframework.web.bind.annotation.RequestHeader("Authorization")
        String token
    ) {

        if ( Boolean.FALSE.equals(
            employeeService.isAuthorized( employeeEmail, token ) ) ) {
            return org.springframework.http.ResponseEntity.status(
                org.springframework.http.HttpStatus.FORBIDDEN ).body( "Unauthorized" );
        }

        String newPassword = passwordRequest.get( "newPassword" );
        String response = employeeService.updatePassword( employeeEmail, newPassword );

        return org.springframework.http.ResponseEntity.ok( response );
    }

}
