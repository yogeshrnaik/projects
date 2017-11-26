package com.raisin.challenge.exception;

/**
 * Exception representing 406 error.
 */
public class NotAcceptableException extends RuntimeException {

    private static final long serialVersionUID = 5751115885419414436L;

    public NotAcceptableException(String message) {
        super(message);
    }

}
