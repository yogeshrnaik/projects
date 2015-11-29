package com.crossover.trial.properties.exception;

public class PropertyException extends RuntimeException {

	private static final long serialVersionUID = 4738154261358524542L;

	public PropertyException(String message) {
		super(message);
	}

	public PropertyException(String message, Throwable t) {
		super(message, t);
	}
}
