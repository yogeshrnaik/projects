package com.crossover.trial.properties.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalFileReader extends ProtocolBasedReader {

	@Override
	public BufferedReader getBufferedReader(String uri) throws IOException {
		if (uri.startsWith("file://")) {
			// file://<Absoulte_Path> - remove "file://"
			uri = uri.substring("file:".length() + 2);
		}

		return new BufferedReader(new InputStreamReader(new FileInputStream(uri)));
	}
}
