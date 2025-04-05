package com.pharmeast.consent.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String message) {
        super("ServiceNotFoundException, " + message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super("ServiceNotFoundException, " + message, cause);
    }

    public ServiceNotFoundException(Throwable cause) {
        super(cause);
    }
}
