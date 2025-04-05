package com.pharmeast.consent.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pharmeast.consent.dto.ApiErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws ServletException, IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado! component customizado | "
//                + accessDeniedException.getMessage());

        ApiErrorDto apiErrorDto = new ApiErrorDto(
                LocalDateTime.now(),
                request.getRequestURI(),
                request.getMethod(),
                HttpServletResponse.SC_FORBIDDEN,
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Acesso negado! CustomAccessDeniedHandler",
                null,
                null
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiErrorDto));
    }
}
