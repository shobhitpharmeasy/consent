package com.pharmeast.consent.repository;

import com.pharmeast.consent.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // Custom query methods can be defined here if needed
}
