package com.matrimony.exceptions;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppException(String errMsg) {
		super(errMsg);
	}

	public AppException(String errMsg, Throwable t) {
		super(errMsg, t);
	}
}
