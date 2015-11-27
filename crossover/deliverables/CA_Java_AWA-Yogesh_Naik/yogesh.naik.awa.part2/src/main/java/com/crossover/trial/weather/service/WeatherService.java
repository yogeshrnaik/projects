package com.crossover.trial.weather.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.Airport;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;

/**
 * Service exposed to the REST end point classes
 * This service internally uses AirportService and RequestStatsService
 * @author Yogesh Naik
 */
public class WeatherService {

	/** Singleton service to manage Airport and its related information */
	private AirportService airportService = AirportService.INSTANCE;

	/** Singleton service to maintain the airport wise & radius wise request frequency statistics */
	private RequestStatsService requestStatsService = RequestStatsService.INSTANCE;

	public long getAtmosphericInfoCountUpdatedInLastDay() {
		return airportService.getCountOfAtmosphericInfoUpdatedInLastDay();
	}

	public Map<String, Double> getFrequencyStats() {
		return requestStatsService.getIataFrequencyStats();
	}

	public Object getRadiusFrequencyStats() {
		return requestStatsService.getIataFrequencyStats();
	}

	public void updateRequestFrequency(String iata, double radius) {
		requestStatsService.updateRequestFrequency(iata, radius);
	}

	public AirportData findAirportData(String iata) {
		return airportService.findAirportData(iata);
	}

	public List<AtmosphericInformation> getAtmosphericInfoForNearByAirports(String iata, double radius) {
		return airportService.getAtmosphericInfoForNearByAirports(iata, radius);
	}

	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException {
		airportService.addDataPoint(iataCode, pointType, dp);
	}

	public List<AirportData> getAirportData() {
		return airportService.getAirportData();
	}

	public List<Airport> getAirports() {
		return getAirportData().stream().map(a -> a.getAirport()).collect(Collectors.toList());
	}

	public void addAirport(String iata, Double latitude, Double longitude) {
		airportService.addAirport(iata, latitude, longitude);
	}

	public void init() {
		airportService.init();
	}
}
