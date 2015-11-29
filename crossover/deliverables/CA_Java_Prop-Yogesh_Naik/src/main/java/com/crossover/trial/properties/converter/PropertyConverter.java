package com.crossover.trial.properties.converter;

/**
 * Interface used to define the contract for converting a property into a desired type.
 *
 * @param <T>
 */
public interface PropertyConverter<T> {

	public static final StringToStringConverter STRING_TO_STRING = new StringToStringConverter();
	/**
	 * Converts from a string value to a specific type
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	T convert(String key, String value);

	public static final class StringToStringConverter implements PropertyConverter<String> {
		@Override
		public String convert(String key, String value) {
			return value;
		}
	}
}
