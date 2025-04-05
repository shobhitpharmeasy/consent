package com.pharmeast.consent.exception;

public class EmployeeNotfoundException extends RuntimeException {
    public EmployeeNotfoundException(String message) {
        super(message);
    }

    public EmployeeNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotfoundException(Throwable cause) {
        super(cause);
    }
}
