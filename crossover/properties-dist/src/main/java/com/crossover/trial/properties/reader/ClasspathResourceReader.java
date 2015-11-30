package com.crossover.trial.properties.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.crossover.trial.properties.exception.PropertyException;

public class ClasspathResourceReader extends ProtocolBasedReader {

	@Override
	public BufferedReader getBufferedReader(String uri) throws IOException {
		if (uri.startsWith("classpath:")) {
			uri = uri.substring("classpath:".length());
		}

		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(uri);
		if (resourceAsStream == null) {
			throw new PropertyException(String.format("Cannot find the resource: [%s] in classpath.", uri));
		}
		return new BufferedReader(new InputStreamReader(resourceAsStream));
	}
}
