package com.pharmeast.consent.dto;

@lombok.Builder
public record ApiErrorDto(

    @com.fasterxml.jackson.annotation.JsonFormat(
        pattern = "dd-MM-yyyy HH:mm:ss"
    ) java.time.LocalDateTime timestamp,

    String path,

    String method,

    Integer status,

    String error,

    String message,

    @com.fasterxml.jackson.annotation.JsonInclude(
        com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
    ) java.util.Map< String, String > fields,

    @com.fasterxml.jackson.annotation.JsonInclude(
        com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
    ) Object stakeTrace
) {

    public ApiErrorDto(
        java.time.LocalDateTime timestamp, String path, String method, Integer status,
        String error, String message
    ) {

        this( timestamp, path, method, status, error, message, null, null );
    }

    public ApiErrorDto(
        java.time.LocalDateTime timestamp, String path, String method, Integer status,
        String error, String message, java.util.Map< String, String > fields,
        Object stakeTrace
    ) {

        this.timestamp = timestamp;
        this.path = path;
        this.method = method;
        this.status = status;
        this.error = error;
        this.message = message;
        this.fields = fields;
        this.stakeTrace = stakeTrace;
    }
}
