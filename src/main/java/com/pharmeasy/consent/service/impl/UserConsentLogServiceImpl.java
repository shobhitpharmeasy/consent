package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.entity.User;
import com.pharmeasy.consent.entity.UserConsentLog;
import com.pharmeasy.consent.repository.UserConsentLogRepository;
import com.pharmeasy.consent.repository.UserRepository;
import com.pharmeasy.consent.service.UserConsentLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserConsentLogServiceImpl
    implements UserConsentLogService {

    private final UserConsentLogRepository userConsentLogRepository;
    private final UserRepository userRepository;

    public UserConsentLogServiceImpl(
        final UserConsentLogRepository userConsentLogRepository,
        final UserRepository userRepository
    ) {
        this.userConsentLogRepository = userConsentLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserConsentLog save(final UserConsentLog userConsentLog) {
        return userConsentLogRepository.save(userConsentLog);
    }

    @Override
    public Optional<UserConsentLog> findById(final Long id) {
        return userConsentLogRepository.findById(id);
    }

    @Override
    public List<UserConsentLog> findAll() {
        return userConsentLogRepository.findAll();
    }

    @Override
    public void deleteById(final Long id) {
        userConsentLogRepository.deleteById(id);
    }

    @Override
    public List<UserConsentLog> findByUser(final String email) {
        User user = userRepository.findById(email).orElseThrow(() -> new RuntimeException("User not found: " + email));
        return userConsentLogRepository.findByUser(user);
    }

}
