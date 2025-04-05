package com.pharmeast.consent.exception;

public class MissingServiceException extends RuntimeException {
    public MissingServiceException(String message) {
        super("MissingServiceException, " + message);
    }

    public MissingServiceException(String message, Throwable cause) {
        super("MissingServiceException, " + message, cause);
    }

    public MissingServiceException(Throwable cause) {
        super(cause);
    }
}
