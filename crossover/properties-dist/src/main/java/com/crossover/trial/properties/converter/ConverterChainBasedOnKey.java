package com.crossover.trial.properties.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.exception.PropertyException;

public class ConverterChainBasedOnKey implements PropertyConverter<Object> {

	private static Map<String, PropertyConverter<?>> converters = new HashMap<>();

	private Properties metadata;

	public ConverterChainBasedOnKey() {
		converters.put("boolean", new StringToBooleanConverter());
		converters.put("integer", new StringToIntegerConverter());
		converters.put("double", new StringToDoubleConverter());
		converters.put("aws_region", new StringToAwsRegionConverter());

		metadata = new Properties();
		try {
			metadata.load(getClass().getClassLoader().getResourceAsStream("metadata.properties"));
		} catch (IOException e) {
			throw new PropertyException("Error initializing metadata: ", e);
		}
	}

	public Object convert(String key, String value) {
		if (value == null)
			return null;

		String metadataKey = (String) metadata.get(key);
		PropertyConverter<?> converter = metadataKey != null ? converters.get(metadataKey)
				: PropertyConverter.STRING_TO_STRING;

		return converter.convert(key, value);
	}
}
