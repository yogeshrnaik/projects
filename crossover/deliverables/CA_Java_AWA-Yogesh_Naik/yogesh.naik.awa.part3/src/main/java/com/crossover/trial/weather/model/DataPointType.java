package com.crossover.trial.weather.model;

/**
 * The various types of data points we can collect.
 * The data point type also contains the valid min (inclusive) and max mean (exclusive) values of that type
 *
 * @author code test administrator
 */
public enum DataPointType {
	WIND(0, Integer.MAX_VALUE),
	TEMPERATURE(-50, 100),
	HUMIDITY(0, 100),
	PRESSURE(650, 800),
	CLOUDCOVER(0, 100),
	PERCIPITATION(0, 100);

	protected final int minMeanInclusive;
	protected final Integer maxMeanExclusive;

	private DataPointType(int minMeanInclusive, int maxMeanExclusive) {
		this.minMeanInclusive = minMeanInclusive;
		this.maxMeanExclusive = maxMeanExclusive;
	}

	public boolean isValid(double mean) {
		return mean >= this.minMeanInclusive && mean < this.maxMeanExclusive;
	}

	public int getMinMeanInclusive() {
		return minMeanInclusive;
	}

	public int getMaxMeanExclusive() {
		return maxMeanExclusive;
	}	
}
