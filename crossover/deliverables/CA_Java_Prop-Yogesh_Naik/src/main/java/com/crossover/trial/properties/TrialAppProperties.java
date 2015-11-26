package com.crossover.trial.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;
import com.crossover.trial.properties.parser.PropertiesParser;
import com.crossover.trial.properties.reader.ProtocolBasedReader;

/**
 * A dummy implementation of TrialAppProperties, this clearly doesn't work. Candidates SHOULD change this class to add their
 * implementation. You are also free to create additional classes
 *
 * note: a default constructor is required.
 *
 * @author code test administrator
 */
public class TrialAppProperties implements AppProperties {

	public Map<String, ProtocolBasedReader> readers;
	public Map<String, PropertiesParser> parsers;

	private Map<Key, Property> props = new TreeMap<>();
	private Map<Key, Property> missingProps = new TreeMap<>();

	public TrialAppProperties(List<String> propUris, Map<String, ProtocolBasedReader> readers,
			Map<String, PropertiesParser> parsers) {
		this.readers = readers;
		this.parsers = parsers;
		propUris.forEach(this::loadProperties);
	}

	private void loadProperties(String propUri) {

		// get reader based on prefix and read the contents
		String prefix = propUri.substring(0, propUri.indexOf(":")).toLowerCase();
		ProtocolBasedReader reader = readers.get(prefix);
		String contents = reader.read(propUri);

		// pass the contents to the parser based on suffix to get properties map
		String suffix = propUri.substring(propUri.lastIndexOf(".") + 1).toLowerCase();
		Map<Key, Property> props = parsers.get(suffix).parse(contents);

		// add properties
		addProperties(props);
	}

	private void addProperties(Map<Key, Property> propsToAdd) {
		filterValidProperties(propsToAdd);
		filterMissingProperties(propsToAdd);

	}

	private void filterValidProperties(Map<Key, Property> propsToFilter) {
		Map<Key, Property> validProps = propsToFilter.entrySet().stream().filter(p -> p.getValue().isValid())
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		props.putAll(validProps);
	}

	private void filterMissingProperties(Map<Key, Property> propsToFilter) {
		Map<Key, Property> missingProps = propsToFilter.entrySet().stream().filter(p -> !p.getValue().isValid())
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		this.missingProps.putAll(missingProps);
	}

	@Override
	public List<String> getMissingProperties() {
		return Collections.unmodifiableList(new ArrayList<Key>(missingProps.keySet()).stream().map(k -> k.getKey())
				.collect(Collectors.toList()));
	}

	@Override
	public List<String> getKnownProperties() {
		return Collections.unmodifiableList(new ArrayList<Key>(props.keySet()).stream().map(k -> k.getKey())
				.collect(Collectors.toList()));
	}

	@Override
	public boolean isValid() {
		return missingProps.size() == 0;
	}

	@Override
	public void clear() {
		props.clear();
		missingProps.clear();
	}

	@Override
	public Object get(String key) {
		return props.get(key).getValue();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Property p : props.values()) {
			result.append(p).append("\n");
		}
		return result.toString();
	}

}
