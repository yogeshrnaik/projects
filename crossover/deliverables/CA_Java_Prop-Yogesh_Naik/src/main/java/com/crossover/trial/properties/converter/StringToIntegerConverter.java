package com.crossover.trial.properties.converter;

public class StringToIntegerConverter implements PropertyConverter<Integer> {

	@Override
	public Integer convert(String key, String value) {
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		String val = value.trim().toLowerCase();

		Integer result = null;
		try {
			result = Integer.valueOf(val);
		} catch (NumberFormatException e) {
			return null;
		}
		return result;
	}
}
