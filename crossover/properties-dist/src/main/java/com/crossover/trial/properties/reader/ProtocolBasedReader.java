package com.crossover.trial.properties.reader;

import java.io.BufferedReader;
import java.io.IOException;

import com.crossover.trial.properties.exception.PropertyException;

public abstract class ProtocolBasedReader {

	public abstract BufferedReader getBufferedReader(String uri) throws IOException;

	public String read(String uri) {
		try (BufferedReader in = getBufferedReader(uri)) {
			return read(in);
		} catch (IOException e) {
			throw new PropertyException(String.format("Error [%s] while reading from [%s]", e.getMessage(), uri), e);
		}
	}

	private String read(BufferedReader in) throws IOException {
		StringBuilder result = new StringBuilder();
		String inputLine = null;
		while ((inputLine = in.readLine()) != null)
			result.append(inputLine).append("\n");
		return result.toString();
	}
}
