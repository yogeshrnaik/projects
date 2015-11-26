package com.crossover.trial.properties.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpBasedReader extends ProtocolBasedReader {
	@Override
	public BufferedReader getBufferedReader(String uri) throws IOException {
		return new BufferedReader(new InputStreamReader(new URL(uri).openStream()));
	}
}
