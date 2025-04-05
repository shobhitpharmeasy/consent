package com.pharmeast.consent.exception;

public class MissingEmailException extends RuntimeException {
    public MissingEmailException(String message) {
        super("MissingEmailException, " + message);
    }
}
