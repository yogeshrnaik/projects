package com.crossover.trial.weather.service;

import java.util.Map;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RequestStatsService {

	/**
	 * Returns the map of IATA as key and the count of number of requests received for that IATA.
	 * 
	 * @return
	 */
	public Map<String, Double> getIataFrequencyStats();

	/**
	 * Returns an array of integers representing the count of requests received for various radius values. Radius = array's
	 * index E.g. number of requests received for radius = 10 will be equal to array's 10th element (array[10]).
	 * 
	 * @return
	 */
	public int[] getRadiusFrequencyStats();

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius);

	/**
	 * clears the statistics of the requests
	 */
	public void clear();

}