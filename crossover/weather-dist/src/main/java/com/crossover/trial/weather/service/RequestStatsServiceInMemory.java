package com.crossover.trial.weather.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.jvnet.hk2.annotations.Service;

import com.crossover.trial.weather.model.AirportData;

/**
 * Service that manages the request frequency by: - Airport IATA - Radius Enum is used to create a singleton object of this
 * service.
 * 
 * @author Yogesh Naik
 *
 */

@Service
public class RequestStatsServiceInMemory implements RequestStatsService {

	public RequestStatsServiceInMemory() {
	}

	public RequestStatsServiceInMemory(AirportService airportService) {
		this.airportService = airportService;
	}

	private static enum Data {
		INSTANCE;

		/** Request frequency by Airport's IATA. ConcurrentHashMap is used to avoid concurrency issues */
		public Map<String, Integer> iataRequestFrequency = new ConcurrentHashMap<String, Integer>();

		/** Stores request frequency for radius. ConcurrentHashMap is used to avoid concurrency issues */
		public Map<Double, Integer> radiusFreq = new ConcurrentHashMap<Double, Integer>();
	}

	@Inject
	private AirportService airportService;

	@Override
	public Map<String, Double> getIataFrequencyStats() {
		Map<String, Double> freq = new HashMap<>();

		int reqFreqSize = Data.INSTANCE.iataRequestFrequency.size();
		for (AirportData data : airportService.getAirportData()) {
			// fraction of queries
			double frac = (double) Data.INSTANCE.iataRequestFrequency.getOrDefault(data.getIata(), 0)
					/ (reqFreqSize == 0 ? 1 : reqFreqSize);
			freq.put(data.getIata(), frac);
		}
		return freq;
	}

	@Override
	public int[] getRadiusFrequencyStats() {
		int m = Data.INSTANCE.radiusFreq.keySet().stream().max(Double::compare).orElse(1000.0).intValue() + 1;

		int[] hist = new int[m];
		for (Map.Entry<Double, Integer> e : Data.INSTANCE.radiusFreq.entrySet()) {
			int i = e.getKey().intValue() % 10;
			hist[i] += e.getValue();
		}
		return hist;
	}

	@Override
	public void updateRequestFrequency(String iata, Double radius) {
		Data.INSTANCE.iataRequestFrequency.put(iata, Data.INSTANCE.iataRequestFrequency.getOrDefault(iata, 0) + 1);
		Data.INSTANCE.radiusFreq.put(radius, Data.INSTANCE.radiusFreq.getOrDefault(radius, 0));
	}

	@Override
	public void clear() {
		Data.INSTANCE.iataRequestFrequency.clear();
		Data.INSTANCE.radiusFreq.clear();
	}
}
