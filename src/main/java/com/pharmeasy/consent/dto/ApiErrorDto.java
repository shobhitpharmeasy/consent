package com.pharmeasy.consent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Builder
public record ApiErrorDto(

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime timestamp,

    String path, String method, Integer status, String error, String message,

    @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, String> fields,

    @JsonInclude(JsonInclude.Include.NON_NULL) Object stakeTrace

) {

    public ApiErrorDto(
        final LocalDateTime timestamp, final String path, final String method, final Integer status,
        final String error, final String message
    ) {
        this(timestamp, path, method, status, error, message, null, null);
        log.error(
            "ApiErrorDto created: [status={}, error={}, message={}, path={}, method={}]", status, error, message,
            path, method
        );
    }

}
