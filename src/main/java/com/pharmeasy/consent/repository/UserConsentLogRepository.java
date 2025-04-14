package com.pharmeasy.consent.repository;

import com.pharmeasy.consent.entity.ConsentType;
import com.pharmeasy.consent.entity.User;
import com.pharmeasy.consent.entity.UserConsentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface UserConsentLogRepository
    extends JpaRepository<UserConsentLog, Long>, RevisionRepository<UserConsentLog, Long, Integer> {

    /**
     * Find all logs for a given User entity
     */
    List<UserConsentLog> findByUser(User user);

    /**
     * Find all logs for a given user email (the User’s PK)
     */
    List<UserConsentLog> findByUserEmail(String email);

    /**
     * If you still need to filter by consent type:
     */
    List<UserConsentLog> findByUserEmailAndConsentType(String email, ConsentType consentType);
}
