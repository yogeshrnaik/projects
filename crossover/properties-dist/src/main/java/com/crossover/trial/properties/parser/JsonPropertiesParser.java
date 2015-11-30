package com.crossover.trial.properties.parser;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.crossover.trial.properties.converter.PropertyConverter;
import com.crossover.trial.properties.exception.PropertyException;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.OrderedProperties;
import com.crossover.trial.properties.model.Property;

public class JsonPropertiesParser extends PropertiesParser {

	public JsonPropertiesParser(PropertyConverter<?> converter) {
		super(converter);
	}

	@Override
	public Map<Key, Property> parse(String jsonStr) {
		if (jsonStr == null || jsonStr.trim().length() == 0) {
			return Collections.emptyMap();
		}

		Properties props = new OrderedProperties();
		try {
			JSONObject json = new JSONObject(jsonStr);
			for (String key : json.keySet()) {
				props.put(key, json.get(key));
			}
		} catch (JSONException e) {
			throw new PropertyException(String.format("Error while parsing [%s]", jsonStr), e);
		}
		return convertToMap(props);
	}
}
