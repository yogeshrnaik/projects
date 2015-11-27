package com.crossover.trial.weather.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.crossover.trial.weather.model.AirportData;

/**
 * Service that manages the request frequency by:
 * - Airport IATA
 * - Radius
 * Enum is used to create a singleton object of this service.
 * @author Yogesh Naik
 *
 */
enum RequestStatsService {
	INSTANCE;

	private AirportService airportService = AirportService.INSTANCE;

	/** Request frequency by Airport's IATA. ConcurrentHashMap is used to avoid concurrency issues */
	public Map<String, Integer> iataRequestFrequency = new ConcurrentHashMap<String, Integer>();

	/** Stores request frequency for radius. ConcurrentHashMap is used to avoid concurrency issues */
	public Map<Double, Integer> radiusFreq = new ConcurrentHashMap<Double, Integer>();

	public Map<String, Double> getIataFrequencyStats() {
		Map<String, Double> freq = new HashMap<>();

		int reqFreqSize = iataRequestFrequency.size();
		for (AirportData data : airportService.getAirportData()) {
			// fraction of queries
			double frac = (double) iataRequestFrequency.getOrDefault(data.getIata(), 0) / (reqFreqSize == 0 ? 1 : reqFreqSize);
			freq.put(data.getIata(), frac);
		}
		return freq;
	}

	public int[] getRadiusFrequencyStats() {
		int m = radiusFreq.keySet().stream().max(Double::compare).orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		return hist;
	}

	/**
	 * Records information about how often requests are made
	 *
	 * @param iata
	 *            an iata code
	 * @param radius
	 *            query radius
	 */
	public void updateRequestFrequency(String iata, Double radius) {
		iataRequestFrequency.put(iata, iataRequestFrequency.getOrDefault(iata, 0) + 1);
		radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0));
	}

	public void clear() {
		iataRequestFrequency.clear();
		radiusFreq.clear();
	}
}
