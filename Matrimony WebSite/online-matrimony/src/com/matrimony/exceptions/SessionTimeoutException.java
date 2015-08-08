package com.matrimony.exceptions;

public class SessionTimeoutException extends AppException {

	private static final long serialVersionUID = 1L;

	public SessionTimeoutException(String errMsg) {
		super(errMsg);
	}

	public SessionTimeoutException(String errMsg, Throwable t) {
		super(errMsg, t);
	}
}
