package com.pharmeast.consent.exception;

public class WrongEmailException extends IllegalArgumentException {
    public WrongEmailException(String message) {
        super("WrongEmailException, " + message);
    }

    public WrongEmailException(String message, Throwable cause) {
        super("WrongEmailException, " + message, cause);
    }
}
