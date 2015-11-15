package com.spoton.esm.exception;

public class ESMException extends RuntimeException {

	public ESMException(String msg) {
		this(msg, null);
	}

	public ESMException(String msg, Throwable t) {
		super(msg, t);
	}
}
