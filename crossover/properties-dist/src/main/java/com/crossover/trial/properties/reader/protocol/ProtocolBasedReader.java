package com.crossover.trial.properties.reader.protocol;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class ProtocolBasedReader {

	public abstract BufferedReader getBufferedReader(String uri) throws IOException;

	public String read(String uri) {
		try (BufferedReader in = getBufferedReader(uri)) {
			return read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String read(BufferedReader in) throws IOException {
		StringBuilder result = new StringBuilder();
		String inputLine = null;
		while ((inputLine = in.readLine()) != null)
			result.append(inputLine).append("\n");
		return result.toString();
	}
}
