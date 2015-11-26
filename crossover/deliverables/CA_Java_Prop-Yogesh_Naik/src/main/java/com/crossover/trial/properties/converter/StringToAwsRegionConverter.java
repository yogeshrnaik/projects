package com.crossover.trial.properties.converter;

import com.amazonaws.regions.Regions;

public class StringToAwsRegionConverter implements PropertyConverter<Regions> {

	@Override
	public Regions convert(String key, String value) {
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		String val = value.trim().toUpperCase().replace("-", "_");

		Regions region = null;
		try {
			region = Regions.valueOf(val);
		} catch (IllegalArgumentException e) {
		}
		return region;
	}
}
