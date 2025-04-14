package com.pharmeasy.consent.service;

import com.pharmeasy.consent.entity.UserConsentLog;

import java.util.List;
import java.util.Optional;

public interface UserConsentLogService {

    UserConsentLog save(final UserConsentLog userConsentLog);

    Optional<UserConsentLog> findById(final Long id);

    List<UserConsentLog> findAll();

    void deleteById(final Long id);

    List<UserConsentLog> findByUser(String email);
}
