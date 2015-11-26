package com.crossover.trial.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;
import com.crossover.trial.properties.reader.AbsoluteFilePathReader;
import com.crossover.trial.properties.reader.ClasspathPropertiesReader;
import com.crossover.trial.properties.reader.HttpPropertiesReader;
import com.crossover.trial.properties.reader.JsonReader;

/**
 * A dummy implementation of TrialAppProperties, this clearly doesn't work. Candidates SHOULD change this class to add their
 * implementation. You are also free to create additional classes
 *
 * note: a default constructor is required.
 *
 * @author code test administrator
 */
public class TrialAppProperties implements AppProperties {

	private static final ConverterChain CONVERTER_CHAIN = new ConverterChain();
	private Map<Key, Property> props = new TreeMap<>();
	private Map<Key, Property> missingProps = new TreeMap<>();

	public TrialAppProperties(List<String> propUris) {
		propUris.forEach(this::loadProperties);
	}

	private void loadProperties(String propUri) {
		if (propUri.startsWith("classpath:") && propUri.endsWith(".properties")) {
			addProperties(new ClasspathPropertiesReader(CONVERTER_CHAIN).read(propUri));
		} else if (propUri.startsWith("file:") && propUri.endsWith(".properties")) {
			addProperties(new AbsoluteFilePathReader(CONVERTER_CHAIN).read(propUri));
		} else if (propUri.startsWith("http:") && propUri.endsWith(".json")) {
			addProperties(new JsonReader(CONVERTER_CHAIN).read(propUri));
		} else if (propUri.startsWith("http:") && propUri.endsWith(".properties")) {
			addProperties(new HttpPropertiesReader(CONVERTER_CHAIN).read(propUri));
		}
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
		return true;
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
