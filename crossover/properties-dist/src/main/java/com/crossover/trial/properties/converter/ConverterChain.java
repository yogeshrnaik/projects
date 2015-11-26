package com.crossover.trial.properties.converter;

import java.util.ArrayList;
import java.util.List;

public class ConverterChain {
	private static List<PropertyConverter<?>> converters = new ArrayList<>();

	public ConverterChain() {
		converters.add(new StringToBooleanConverter());
		converters.add(new StringToIntegerConverter());
		converters.add(new StringToDoubleConverter());
		converters.add(new StringToAwsRegionConverter());
	}

	public Object convertToActualType(String key, Object value) {
		if (value == null)
			return null;

		for (PropertyConverter<?> converter : converters) {
			Object result = converter.convert(key, value.toString());
			if (result != null) {
				return result;
			}
		}

		return value;
	}
}
