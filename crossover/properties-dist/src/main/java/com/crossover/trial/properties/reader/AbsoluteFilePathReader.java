package com.crossover.trial.properties.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.crossover.trial.properties.converter.ConverterChain;

public class AbsoluteFilePathReader extends PropertiesReader {

	public AbsoluteFilePathReader(ConverterChain converterChain) {
		super(converterChain);
	}

	@Override
	Properties readProperties(String propertyResourceUri) {
		Properties props = new OrderedProperties();
		try {
			// file://<Absoulte_Path> - remove "file://"
			String file = propertyResourceUri.substring("file:".length() + 2);
			props.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
