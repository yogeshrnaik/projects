package com.crossover.trial.properties.model;

public class Property {

	private final String key;
	private final Object value;

	public Property(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", key, value.getClass().getCanonicalName(), value.toString());
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public boolean isValid() {
		return value != null && value.toString().trim().length() > 0;
	}

}
