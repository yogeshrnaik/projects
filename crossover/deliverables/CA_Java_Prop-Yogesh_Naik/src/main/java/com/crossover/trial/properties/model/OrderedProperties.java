package com.crossover.trial.properties.model;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class OrderedProperties extends Properties {

	private static final long serialVersionUID = -3366937601047626654L;

	private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

	@Override
	public Enumeration<?> propertyNames() {
		return Collections.enumeration(keys);
	}

	@Override
	public synchronized Enumeration<Object> elements() {
		return Collections.enumeration(keys);
	}

	public Enumeration<Object> keys() {
		return Collections.enumeration(keys);
	}

	public Object put(Object key, Object value) {
		if (keys.contains(key)) {
			// remove old one first
			keys.remove(key);
			super.remove(key);
		}

		keys.add(key);
		return super.put(key, value);
	}

	@Override
	public synchronized Object remove(Object key) {
		keys.remove(key);
		return super.remove(key);
	}

	@Override
	public synchronized void clear() {
		keys.clear();
		super.clear();
	}

	@Override
	public Set<String> stringPropertyNames() {
		LinkedHashSet<String> result = new LinkedHashSet<String>();
		for (Object o : keys) {
			result.add(o.toString());
		}
		return Collections.unmodifiableSet(result);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Object key : keys) {
			result.append(key).append("=").append(super.get(key)).append(", ");
		}

		if (result.length() > 0) {
			result.delete(result.length() - 2, result.length());
		}

		return result.toString();
	}
}