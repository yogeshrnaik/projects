package com.crossover.trial.weather.service;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.Airport;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;

/**
 * Service interface to manage and query Airport related information
 * 
 * @author Yogesh Naik
 */
@Contract
public interface AirportService {

	public void init();

	public List<Airport> getAirports();

	public AirportData findAirportData(String iataCode);

	public List<AtmosphericInformation> getAtmosphericInfoForNearByAirports(String iata, double radius);

	public List<AtmosphericInformation> getAtmosphericInformation();

	public long getCountOfAtmosphericInfoUpdatedInLastDay();

	public List<AirportData> getAirportData();

	public AirportData addAirport(String iataCode, double latitude, double longitude);

	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException;

	public boolean isUpdatedInLastDay(AtmosphericInformation ai);

	public AirportData deleteAirport(String iata);
	
}