package com.retail.store.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 634867904246662427L;

    public NotFoundException(String messageKey, Throwable t) {
        super(messageKey, t);
    }
}
