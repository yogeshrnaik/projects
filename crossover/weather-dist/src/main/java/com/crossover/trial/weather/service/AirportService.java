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

	/**
	 * Returns a list of Airports Returns empty list in case no airport is currently present.
	 * 
	 * @return
	 */
	public List<Airport> getAirports();

	/**
	 * Finds an airport by IATA code. Returns null if matching airport is not found.
	 * 
	 * @param iataCode
	 * @return
	 */
	public AirportData findAirportData(String iataCode);

	/**
	 * Get the list of Atmospheric information of all the airports available in the system. Returns empty list if no airport
	 * is currently present in the system.
	 * 
	 * @return
	 */
	public List<AtmosphericInformation> getAtmosphericInformation();

	/**
	 * Returns a list of Atmospheric information of all the airports within a radius specified of the given airport
	 * (mentioned by IATA code)
	 * 
	 * if radius == 0 then it returns only the AtmosphericInformation of the airport matching the IATA parameter
	 * 
	 * @param iata
	 * @param radius
	 * @return
	 */
	public List<AtmosphericInformation> getAtmosphericInformation(String iata, double radius);

	/**
	 * Returns the count of Atmospheric information that was updated in the last day. Returns 0 in case no update was done in
	 * last day.
	 * 
	 * @return
	 */
	public long getCountOfAtmosphericInfoUpdatedInLastDay();

	/**
	 * Returns the list of AirportData of all airports, containing Airport's basic info + its Atmospheric information
	 * 
	 * @return
	 */
	public List<AirportData> getAirportData();

	/**
	 * Adds an airport to the system using IATA, latitude and longitude.
	 * 
	 * @param iataCode
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public AirportData addAirport(String iataCode, double latitude, double longitude);

	/**
	 * Add an Atmospheric data point to an airport specified by its IATA code.
	 * 
	 * @param iataCode
	 * @param pointType
	 * @param dp
	 * @throws WeatherException
	 *             in case the data point values are not within range for the specified DataPointType
	 */
	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException;

	/**
	 * Delete the airport by its IATA code. Returns the deleted airport. If there was no airport to delete then returns null.
	 * 
	 * @param iata
	 * @return
	 */
	public AirportData deleteAirport(String iata);

}