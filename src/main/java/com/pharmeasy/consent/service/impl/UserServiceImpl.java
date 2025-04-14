package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.dto.UserDto;
import com.pharmeasy.consent.entity.ConsentType;
import com.pharmeasy.consent.entity.User;
import com.pharmeasy.consent.entity.UserConsentLog;
import com.pharmeasy.consent.mapper.UserMapper;
import com.pharmeasy.consent.repository.UserConsentLogRepository;
import com.pharmeasy.consent.repository.UserRepository;
import com.pharmeasy.consent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConsentLogRepository logRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(final UserDto userDto) {
        log.info("Creating user: {}", userDto.getEmail());
        if (userRepository.existsById(userDto.getEmail())) {
            log.warn("User already exists: {}", userDto.getEmail());
            throw new RuntimeException("User already exists: " + userDto.getEmail());
        }
        // Map DTO → entity (will ignore `deleted`)
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        log.info("User created: {}", saved.getEmail());
        return userMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll()
                             .stream()
                             .map(userMapper::toDto)
                             .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserByEmail(final String email) {
        log.info("Fetching user by email: {}", email);
        User user = userRepository.findById(email)
                                  .orElseThrow(() -> {
                                      log.warn("User not found: {}", email);
                                      return new RuntimeException("User not found: " + email);
                                  });
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(final String email, final UserDto userDto) {
        log.info("Updating user: {}", email);
        User existing = userRepository.findById(email)
                                      .orElseThrow(() -> {
                                          log.warn("User not found for update: {}", email);
                                          return new RuntimeException("User not found: " + email);
                                      });

        if (userDto.getFirstName() != null)  existing.setFirstName(userDto.getFirstName());
        if (userDto.getMiddleName() != null) existing.setMiddleName(userDto.getMiddleName());
        if (userDto.getLastName() != null)   existing.setLastName(userDto.getLastName());
        if (userDto.getDataConsents() != null) {
            existing.setDataConsents(userDto.getDataConsents());
        }

        User updated = userRepository.save(existing);
        log.info("User updated: {}", updated.getEmail());
        return userMapper.toDto(updated);
    }

    @Override
    @Transactional
    public String deleteUser(final String email) {
        log.info("Soft‑deleting user: {}", email);
        User user = userRepository.findById(email)
                                  .orElseThrow(() -> {
                                      log.warn("Attempt to delete non‑existent user: {}", email);
                                      return new RuntimeException("User not found: " + email);
                                  });
        // mark logs deleted
        user.getConsentLogs().forEach(log -> log.setDeleted(true));
        // soft delete user
        userRepository.delete(user);
        log.info("User soft‑deleted: {}", email);
        return MessageFormat.format("User deleted: {0}", email);
    }

    @Override
    @Transactional
    public Boolean giveConsent(final String email,
                               final ConsentType consentType,
                               final Boolean consented) {
        log.info("User {} setting consent {} → {}", email, consentType, consented);
        User user = userRepository.findById(email)
                                  .orElseThrow(() -> {
                                      log.warn("User not found for consent: {}", email);
                                      return new RuntimeException("User not found: " + email);
                                  });

        Boolean current = user.getDataConsents().get(consentType);
        if (consented.equals(current)) {
            log.debug("No change in consent for {}: still {}", consentType, consented);
            return false;
        }

        // update map
        user.getDataConsents().put(consentType, consented);
        userRepository.save(user);

        // log the change
        UserConsentLog logEntry = UserConsentLog.builder()
                                                .user(user)
                                                .consentType(consentType)
                                                .consented(consented)
                                                .timestamp(LocalDateTime.now())
                                                .build();
        logRepository.save(logEntry);

        log.info("Consent {} for user {} updated to {}", consentType, email, consented);
        return true;
    }
}
