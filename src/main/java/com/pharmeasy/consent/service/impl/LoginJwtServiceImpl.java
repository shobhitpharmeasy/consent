package com.pharmeasy.consent.service.impl;

import com.pharmeasy.consent.dto.LoginRequestDto;
import com.pharmeasy.consent.entity.Employee;
import com.pharmeasy.consent.repository.EmployeeRepository;
import com.pharmeasy.consent.service.LoginJwtService;
import com.pharmeasy.consent.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginJwtServiceImpl
    implements LoginJwtService {

    private final EmployeeRepository employeeRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(final LoginRequestDto loginRequestDto) {
        log.info("Attempting login for email: {}", loginRequestDto.getEmail());

        final Employee employee = employeeRepository.findById(loginRequestDto.getEmail()).orElseThrow(
            () -> new BadCredentialsException("Invalid email or password"));

        if (Boolean.FALSE.equals(passwordEncoder.matches(loginRequestDto.getPassword(), employee.getPasswordHash()))) {
//            throw new BadCredentialsException("Invalid email or password");
            log.error("Invalid password for email: {}", loginRequestDto.getEmail());
            throw new RuntimeException("Invalid password");
        }

        final String token = jwtUtils.generateToken(employee.getEmail());
        log.info("JWT generated for email: {}", employee.getEmail());

        return token;
    }

    @Override
    public Boolean isAuthorized(final LoginRequestDto loginRequestDto) {
//        return Boolean.TRUE;
        return jwtUtils.validateToken(loginRequestDto.getToken(), loginRequestDto.getEmail());
    }

}
