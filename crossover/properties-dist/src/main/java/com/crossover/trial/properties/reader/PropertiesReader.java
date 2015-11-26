package com.crossover.trial.properties.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;

public abstract class PropertiesReader {

	private ConverterChain converterChain;

	public PropertiesReader(ConverterChain converterChain) {
		this.converterChain = converterChain;
	}

	/**
	 * Reads a property file
	 * 
	 * @param propertyResourceUri
	 * @return all the properties defined in the file
	 */
	abstract Properties readProperties(String propertyResourceUri);

	/**
	 * Read property file and return a map of Key as String and value as Property object
	 * 
	 * @param propFile
	 * @return
	 */
	public Map<Key, Property> read(String propFile) {
		Properties props = readProperties(propFile);
		return convertToMap(props);
	}

	protected Map<Key, Property> convertToMap(Properties props) {
		Map<Key, Property> result = new HashMap<>();
		for (String key : props.stringPropertyNames()) {
			result.put(new Key(key), new Property(key, converterChain.convertToActualType(props.get(key))));
		}
		return result;
	}

	protected List<String> readFromUrl(String resourceUrl) {
		String inputLine = null;
		List<String> result = new ArrayList<>();
		BufferedReader in = null;
		try {
			URL url = new URL(resourceUrl);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((inputLine = in.readLine()) != null)
				result.add(inputLine);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(in);
		}
		return result;
	}

	private void close(Reader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String concat(List<String> vals) {
		StringBuilder result = new StringBuilder();
		for (String s : vals) {
			result.append(s).append("\n");
		}
		return result.toString();
	}
}
