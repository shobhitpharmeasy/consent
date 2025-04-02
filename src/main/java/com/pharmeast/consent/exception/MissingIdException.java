package com.pharmeast.consent.exception;

public class MissingIdException extends RuntimeException {
    public MissingIdException(String message) {
        super("MissingIdException, " + message);
    }
}
