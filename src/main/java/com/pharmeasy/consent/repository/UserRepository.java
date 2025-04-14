package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository
    extends JpaRepository<User, String> {

    // Alias for convenience
    default Optional<User> findByEmail(final String email) {
        return findById(email);
    }

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.deleted = true")
    Optional<User> findDeletedByEmail(@Param("email") String email);
}
