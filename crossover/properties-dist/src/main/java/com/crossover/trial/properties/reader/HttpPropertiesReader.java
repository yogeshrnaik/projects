package com.crossover.trial.properties.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import com.crossover.trial.properties.converter.ConverterChain;

public class HttpPropertiesReader extends PropertiesReader {

	public HttpPropertiesReader(ConverterChain converterChain) {
		super(converterChain);
	}

	@Override
	Properties readProperties(String propertyResourceUri) {
		List<String> properties = readFromUrl(propertyResourceUri);
		if (properties == null || properties.size() == 0) {
			return new Properties();
		}

		String propertiesString = concat(properties);
		Properties props = new OrderedProperties();
		try {
			props.load(new StringReader(propertiesString));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}
