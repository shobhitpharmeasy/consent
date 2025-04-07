package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository
    extends JpaRepository<Employee, String> {

    // Optional alias for consistency or legacy use
    default Optional<Employee> findByEmail(final String email) {
        return findById(email);
    }

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
