package com.crossover.trial.weather.service;

import java.util.Map;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RequestStatsService {

	public abstract Map<String, Double> getIataFrequencyStats();

	public abstract int[] getRadiusFrequencyStats();

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public abstract void updateRequestFrequency(String iata, Double radius);

	public abstract void clear();

}