package com.pharmeast.consent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static String getStackTrace(Exception ex) {
        StringBuilder trace = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            trace.append(element.toString()).append("\n");
        }
        return trace.toString();
    }

    private static ResponseEntity<Map<String, Object>> buildErrorResponse(
        Exception ex, HttpStatus status, WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));
        // errorResponse.put("trace", getStackTrace(ex));  // Include stack trace

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameterException(
        MissingParameterException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MissingIdException.class)
    public ResponseEntity<Map<String, Object>> handleMissingIdException(
        MissingIdException ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
        Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
