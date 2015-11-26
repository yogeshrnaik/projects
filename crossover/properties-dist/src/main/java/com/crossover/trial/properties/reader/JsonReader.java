package com.crossover.trial.properties.reader;

import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

import com.crossover.trial.properties.converter.ConverterChain;

public class JsonReader extends PropertiesReader {

	public JsonReader(ConverterChain converterChain) {
		super(converterChain);
	}

	@Override
	Properties readProperties(String propertyResourceUri) {
		List<String> jsonAsList = readFromUrl(propertyResourceUri);
		if (jsonAsList == null || jsonAsList.size() == 0) {
			return new Properties();
		}

		String json = concat(jsonAsList);
		JSONObject obj = new JSONObject(json);
		Properties props = new OrderedProperties();
		for (String key : obj.keySet()) {
			props.put(key, obj.get(key));
		}

		return props;
	}
}
