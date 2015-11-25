package com.crossover.trial.properties.reader;

import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;

public abstract class PropertiesReader {

	private ConverterChain converterChain;

	public PropertiesReader(ConverterChain converterChain) {
		this.converterChain = converterChain;
	}

	/**
	 * Reads a property file
	 * 
	 * @param propertyFileName
	 * @return all the properties defined in the file
	 */
	abstract Properties readProperties(String propertyFileName);

	/**
	 * Read property file and return a map of Key as String and value as Property object
	 * 
	 * @param propFile
	 * @return
	 */
	public Map<Key, Property> read(String propFile) {
		Properties props = readProperties(propFile);
		return convertToMap(props);
	}

	protected Map<Key, Property> convertToMap(Properties props) {
		Map<Key, Property> result = new TreeMap<Key, Property>();
		for (String key : props.stringPropertyNames()) {
			result.put(new Key(key), new Property(key, converterChain.convertToActualType(props.get(key))));
		}
		return result;
	}
}
