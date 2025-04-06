package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository
    extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmail(String email);

    @Query(
        """
                SELECT e FROM Employee e
                LEFT JOIN FETCH e.ownedServices os
                WHERE e.email = :email
            """
    )
    Optional<Employee> findByEmailWithOwnedServices(@Param("email") String email);

    @Query(
        """
                SELECT s FROM Service s
                JOIN s.employeeAccess ea
                WHERE KEY(ea).email = :email AND ea = com.pharmeasy.consent.entity.Service.AccessStatus.REQUESTED
            """
    )
    List<Service> findRequestedServicesByEmployeeEmail(@Param("email") String email);

    @Query(
        """
                SELECT s FROM Service s
                JOIN s.employeeAccess ea
                WHERE KEY(ea).email = :email AND ea = com.pharmeasy.consent.entity.Service.AccessStatus.ACCEPTED
            """
    )
    List<Service> findAccessibleServicesByEmployeeEmail(@Param("email") String email);

}
