package com.crossover.trial.properties.converter;

import java.util.ArrayList;
import java.util.List;

public class ConverterChain {
	private static List<Converter<?>> converters = new ArrayList<>();

	public ConverterChain() {
		converters.add(new StringToBooleanConverter());
		converters.add(new StringToIntegerConverter());
		converters.add(new StringToDoubleConverter());
	}

	public Object convertToActualType(Object value) {
		if (value == null)
			return null;

		for (Converter<?> converter : converters) {
			Object result = converter.convert(value.toString());
			if (result != null) {
				return result;
			}
		}

		return value;
	}
}
