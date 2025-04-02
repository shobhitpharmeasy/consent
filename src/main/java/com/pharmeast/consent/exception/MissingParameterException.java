package com.pharmeast.consent.exception;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException(String message) {
        super("MissingParameterException, " + message);
    }
}
