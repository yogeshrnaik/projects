package com.crossover.trial.properties.reader.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClasspathResourceReader extends ProtocolBasedReader {

	@Override
	public BufferedReader getBufferedReader(String uri) throws IOException {
		if (uri.startsWith("classpath:")) {
			uri = uri.substring("classpath:".length());
		}

		return new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(uri)));
	}
}
