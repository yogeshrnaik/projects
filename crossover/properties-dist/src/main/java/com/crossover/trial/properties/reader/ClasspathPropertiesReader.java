package com.crossover.trial.properties.reader;

import java.io.IOException;
import java.util.Properties;

import com.crossover.trial.properties.converter.ConverterChain;

public class ClasspathPropertiesReader extends PropertiesReader {

	public ClasspathPropertiesReader(ConverterChain converterChain) {
		super(converterChain);
	}

	public Properties readProperties(String propFile) {
		Properties props = new OrderedProperties();
		try {
			props.load(getClass().getClassLoader().getResourceAsStream(propFile.substring("classpath:".length())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
