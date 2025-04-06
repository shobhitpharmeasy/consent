package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository
    extends JpaRepository<Service, String> {

    Optional<Service> findByName(String name);

    @Query(
        """
                SELECT s FROM Service s
                JOIN s.employeeAccess ea
                WHERE KEY(ea).email = :email
            """
    )
    List<Service> findAllServicesByEmployeeEmail(@Param("email") String email);
}
