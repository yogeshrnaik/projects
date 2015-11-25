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
	Properties readProperties(String propertyFilePath) {
		Properties props = new Properties();
		try {
			// file://<Absoulte_Path> - remove "file://"
			String file = propertyFilePath.substring("file:".length() + 2);
			props.load(new FileInputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
