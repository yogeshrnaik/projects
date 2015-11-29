package com.crossover.trial.properties.converter;

import java.util.ArrayList;
import java.util.List;

public class ConverterChain implements PropertyConverter<Object> {

	private static List<PropertyConverter<?>> converters = new ArrayList<>();

	public ConverterChain() {
		converters.add(new StringToBooleanConverter());
		converters.add(new StringToIntegerConverter());
		converters.add(new StringToDoubleConverter());
		converters.add(new StringToAwsRegionConverter());
	}

	@Override
	public Object convert(String key, String value) {
		if (value == null)
			return null;

		for (PropertyConverter<?> converter : converters) {
			Object result = converter.convert(key, value);
			if (result != null) {
				return result;
			}
		}

		return value;
	}
}
