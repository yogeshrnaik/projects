package com.crossover.trial.properties.parser;

import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;
import com.crossover.trial.properties.reader.OrderedProperties;

public class JsonPropertiesParser extends PropertiesParser {

	public JsonPropertiesParser(ConverterChain converterChain) {
		super(converterChain);
	}

	@Override
	public Map<Key, Property> parse(String jsonStr) {
		Properties props = new OrderedProperties();
		try {
			JSONObject json = new JSONObject(jsonStr);
			for (String key : json.keySet()) {
				props.put(key, json.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertToMap(props);
	}
}
