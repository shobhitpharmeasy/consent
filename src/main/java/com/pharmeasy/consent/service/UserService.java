package com.pharmeasy.consent.service;

import com.pharmeasy.consent.dto.UserDto;
import com.pharmeasy.consent.entity.ConsentType;

import java.util.List;

public interface UserService {

    UserDto createUser(final UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUserByEmail(final String email);

    UserDto updateUser(final String email, final UserDto userDto);

    String deleteUser(final String email);

    /**
     * Grant or revoke a single consent.
     * Returns true if the consent value actually changed.
     */
    Boolean giveConsent(final String email,
                        final ConsentType consentType,
                        final Boolean consented);
}
