package com.crossover.trial.properties;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.converter.PropertyConverter;
import com.crossover.trial.properties.parser.JsonPropertiesParser;
import com.crossover.trial.properties.parser.PropertiesParser;
import com.crossover.trial.properties.reader.ClasspathResourceReader;
import com.crossover.trial.properties.reader.HttpBasedReader;
import com.crossover.trial.properties.reader.LocalFileReader;
import com.crossover.trial.properties.reader.ProtocolBasedReader;

/**
 * A simple main method to load and print properties. You should feel free to change this class or to create additional
 * class. You may add addtional methods, but must implement the AppPropertiesManager API contract.
 * 
 * Note: a default constructor is required
 *
 * @author code test administrator
 */
public class TrialAppPropertiesManager implements AppPropertiesManager {

	public static final Map<String, ProtocolBasedReader> readers = new HashMap<>();
	public static final Map<String, PropertiesParser> parsers = new HashMap<>();
	private static final PropertyConverter<?> CONVERTER_CHAIN = new ConverterChain();

	static {
		readers.put("classpath", new ClasspathResourceReader());
		readers.put("file", new LocalFileReader());
		readers.put("http", new HttpBasedReader());

		parsers.put("properties", new PropertiesParser(CONVERTER_CHAIN));
		parsers.put("json", new JsonPropertiesParser(CONVERTER_CHAIN));
	}

	public TrialAppPropertiesManager() {
	}

	@Override
	public AppProperties loadProps(List<String> propUris) {
		return new TrialAppProperties(propUris, readers, parsers);
	}

	@Override
	public void printProperties(AppProperties props, PrintStream sync) {
		sync.println(props);
		System.out.println(props);
	}
}
