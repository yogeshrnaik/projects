package com.crossover.trial.properties.model;

public class Property {

	private final String key;
	private final String value;
	private final Object typeSafeValue;

	public Property(String key, String value, Object typeSafeValue) {
		this.key = key;
		this.value = value;
		this.typeSafeValue = typeSafeValue;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s", key, typeSafeValue.getClass().getCanonicalName(), value);
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public Object getTypeSafeValue() {
		return typeSafeValue;
	}

	public boolean isValid() {
		return typeSafeValue != null && typeSafeValue.toString().trim().length() > 0;
	}

}
