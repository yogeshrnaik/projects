package com.crossover.trial.properties.converter;

public class StringToBooleanConverter implements Converter<Boolean> {

	@Override
	public Boolean convert(String value) {
		if (value == null || value.trim().length() == 0) {
			return null;
		}

		String val = value.trim().toLowerCase();

		return (val.equals("true") || val.equals("false")) ? Boolean.valueOf(val) : null;
	}
}
