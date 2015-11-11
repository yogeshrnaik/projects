package com.spoton.esm.exception;

public class ESMException extends Exception {

	public ESMException(String msg) {
		this(msg, null);
	}

	public ESMException(String msg, Throwable t) {
		super(msg, t);
	}
}
