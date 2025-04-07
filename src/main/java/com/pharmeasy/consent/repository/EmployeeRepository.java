package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository
    extends JpaRepository<Employee, String>, RevisionRepository<Employee, String, Integer> {

    // Optional alias for consistency or legacy use
    default Optional<Employee> findByEmail(final String email) {
        return findById(email);
    }

    @Query("SELECT e FROM Employee e WHERE e.email = :email AND e.deleted = true")
    Optional<Employee> findDeletedByEmail(@Param("email") String email);

    // Eagerly fetch all related services
//    @Override
//    @EntityGraph(
//        attributePaths = {
//            "ownedServices", "requestedServices", "accessibleServices"
//        }
//    )
//    @NonNull
//    Optional<Employee> findById(@Nullable String email);
}
