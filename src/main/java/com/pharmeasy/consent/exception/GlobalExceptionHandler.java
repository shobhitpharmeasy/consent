package com.pharmeasy.consent.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for managing application exceptions.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException and returns a standardized response.
     *
     * @param ex the EntityNotFoundException
     * @return ResponseEntity with error details and HTTP status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                                                        System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Define other exception handlers as needed

    /**
     * Inner class to represent error response details.
     */
    public static class ErrorResponse {

        private final int status;
        private final String message;
        private final long timestamp;

        public ErrorResponse(int status, String message, long timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
            log.error("Error occurred: {} at {} - {}", status, timestamp, message);
        }

        // Getters and setters
    }
}
