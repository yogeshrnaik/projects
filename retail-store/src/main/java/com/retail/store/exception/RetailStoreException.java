package com.retail.store.exception;

public class RetailStoreException extends RuntimeException {

    private static final long serialVersionUID = 634867904246662427L;

    public RetailStoreException(String messageKey, Throwable t) {
        super(messageKey, t);
    }
}
