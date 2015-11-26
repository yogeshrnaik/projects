package com.crossover.trial.properties.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.OrderedProperties;
import com.crossover.trial.properties.model.Property;

public class PropertiesParser {

	private final ConverterChain converterChain;

	public PropertiesParser(ConverterChain converterChain) {
		this.converterChain = converterChain;
	}

	public Map<Key, Property> parse(String propertiesString) {
		Properties properties = new OrderedProperties();
		try {
			properties.load(new StringReader(propertiesString));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convertToMap(properties);
	}

	protected Map<Key, Property> convertToMap(Properties props) {
		Map<Key, Property> result = new HashMap<>();
		for (String key : props.stringPropertyNames()) {
			result.put(new Key(key), new Property(key, converterChain.convertToActualType(props.get(key))));
		}
		return result;
	}
}
