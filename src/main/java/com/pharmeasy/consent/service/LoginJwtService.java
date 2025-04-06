package com.pharmeasy.consent.service;

import com.pharmeasy.consent.dto.LoginRequestDto;

public interface LoginJwtService {

    String login(LoginRequestDto requestDto);

    Boolean isAuthorized(LoginRequestDto requestDto);
}
