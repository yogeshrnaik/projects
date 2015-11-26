package com.crossover.trial.properties.converter;

public interface PropertyConverter<T> {

	T convert(String key, String value);
}
