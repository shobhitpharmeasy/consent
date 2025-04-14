package com.pharmeasy.consent.mapper.helper;

import com.pharmeasy.consent.entity.User;
import com.pharmeasy.consent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperHelper {

    private final UserRepository userRepository;

    public String userToString(User user) {
        return user != null ? user.getEmail() : null;
    }

    public User stringToUser(String email) {
        if (email == null) return null;
        return userRepository.findById(email).orElse(User.builder().email(email).build());
    }
}
