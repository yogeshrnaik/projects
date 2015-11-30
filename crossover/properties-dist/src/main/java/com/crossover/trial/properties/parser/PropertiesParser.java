package com.crossover.trial.properties.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.converter.PropertyConverter;
import com.crossover.trial.properties.exception.PropertyException;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.OrderedProperties;
import com.crossover.trial.properties.model.Property;

public class PropertiesParser {

	private final PropertyConverter<?> converter;

	public PropertiesParser(PropertyConverter<?> converter) {
		this.converter = converter;
	}

	public Map<Key, Property> parse(String propertiesString) {
		if (propertiesString == null) {
			return Collections.emptyMap();
		}

		Properties properties = new OrderedProperties();
		try {
			properties.load(new StringReader(propertiesString));
		} catch (IOException e) {
			throw new PropertyException(String.format("Error [%s] while parsing contents: [%s]", e.getMessage(),
					propertiesString), e);
		}
		return convertToMap(properties);
	}

	public Map<Key, Property> convertToMap(Properties props) {
		Map<Key, Property> result = new HashMap<>();
		for (String key : props.stringPropertyNames()) {
			String value = props.get(key).toString();
			result.put(new Key(key), new Property(key, value, converter.convert(key, value)));
		}
		return result;
	}
}
