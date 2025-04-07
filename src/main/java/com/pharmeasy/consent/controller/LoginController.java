package com.pharmeasy.consent.controller;

import com.pharmeasy.consent.dto.LoginRequestDto;
import com.pharmeasy.consent.service.LoginJwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login_jwt")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Login json API")
public class LoginController {

    private final LoginJwtService loginService;

    @PostMapping
    @Operation(summary = "Authenticate user and return JWT")
    //@Cacheable(value = "loginTokens", key = "#requestDto.email + ':' + #requestDto.password")
    public ResponseEntity<String> login(@Valid @RequestBody final LoginRequestDto requestDto) {
        log.info("Login request received for email: {}", requestDto.getEmail());
        final String response = loginService.login(requestDto);
        return ResponseEntity.ok(response);
    }
}
