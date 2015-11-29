package com.crossover.trial.weather.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.crossover.trial.weather.model.AirportData;

/**
 * Service that manages the request frequency by: Airport IATA and Radius
 * 
 * @author Yogesh Naik
 */

@Service
public class RequestStatsServiceInMemory implements RequestStatsService {

	public RequestStatsServiceInMemory() {
	}

	public RequestStatsServiceInMemory(AirportService airportService) {
		this.airportService = airportService;
	}

	private static final RequestStats reqStats = RequestStats.INSTANCE;

	/**
	 * Enum is used to create a singleton in memory data place holder object.
	 */
	private static enum RequestStats {
		INSTANCE;

		/** Request frequency by Airport's IATA. ConcurrentHashMap is used to avoid concurrency issues */
		public Map<String, Integer> iataRequestFrequency = new ConcurrentHashMap<>();

		/** Stores request frequency for radius. ConcurrentHashMap is used to avoid concurrency issues */
		public Map<Double, Integer> radiusFreq = new ConcurrentHashMap<>();

		private void incrementIataRequestFrequency(String iata) {
			iataRequestFrequency.put(iata, iataRequestFrequency.getOrDefault(iata, 0) + 1);
		}

		public void incrementRadiusFrequency(Double radius) {
			radiusFreq.put(radius, radiusFreq.getOrDefault(radius, 0) + 1);
		}

		public void clear() {
			iataRequestFrequency.clear();
			radiusFreq.clear();
		}
	}

	@Inject
	private AirportService airportService;

	@Override
	public Map<String, Double> getIataFrequencyStats() {
		Map<String, Double> freq = new HashMap<>();

		int reqFreqSize = reqStats.iataRequestFrequency.size();
		for (AirportData data : airportService.getAirportData()) {
			// fraction of queries
			double frac = (double) reqStats.iataRequestFrequency.getOrDefault(data.getIata(), 0)
					/ (reqFreqSize == 0 ? 1 : reqFreqSize);
			freq.put(data.getIata(), frac);
		}
		return freq;
	}

	@Override
	public int[] getRadiusFrequencyStats() {
		int m = reqStats.radiusFreq.keySet().stream().max(Double::compare).orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : reqStats.radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		return hist;
	}

	@Override
	public void updateRequestFrequency(String iata, Double radius) {
		reqStats.incrementIataRequestFrequency(iata);
		reqStats.incrementRadiusFrequency(radius);
	}

	@Override
	public void clear() {
		reqStats.clear();
	}
}
