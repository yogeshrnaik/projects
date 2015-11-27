package com.crossover.trial.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.Constants;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.util.GeoLocationUtils;

/**
 * Service that manages the airport and airport's atmospheric data
 * Enum is used to create a singleton object of this service.
 * @author Yogesh Naik
 *
 */
enum AirportService {
	INSTANCE;

	/** airport data stored in ConcurrentHashMap to avoid concurrency issues */
	private Map<String, AirportData> airportData = new ConcurrentHashMap<>();

	private AirportService() {
		init();
	}

	/**
	 * Loads sample data from a file
	 */
	public void init() {
		clear();

		String l = null;
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("airports.dat");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
			while ((l = br.readLine()) != null) {
				String[] split = l.split(",");
				AirportData ad = addAirport(split[0], Double.valueOf(split[1]), Double.valueOf(split[2]));
				System.out.println("Airport added: " + ad.getAirport());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AirportData findAirportData(String iataCode) {
		return airportData.get(iataCode);
	}

	public List<AtmosphericInformation> getAtmosphericInfoForNearByAirports(String iata, double radius) {
		AirportData source = findAirportData(iata);

		return airportData.values().stream()
				.filter(a -> GeoLocationUtils.isWithinRadius(source.getLocation(), a.getLocation(), radius))
				.map(a -> a.getAtmosphericInfo()).filter(ai -> ai.hasReading()).collect(Collectors.toList());
	}

	public List<AtmosphericInformation> getAtmosphericInformation() {
		return airportData.values().stream().map(a -> a.getAtmosphericInfo()).collect(Collectors.toList());
	}

	public long getCountOfAtmosphericInfoUpdatedInLastDay() {
		return getAtmosphericInformation().stream().filter(ai -> ai.hasReading() && isUpdatedInLastDay(ai)).count();
	}

	public List<AirportData> getAirportData() {
		return Collections.unmodifiableList(Arrays.asList(airportData.values().toArray(new AirportData[0])));
	}

	public AirportData addAirport(String iataCode, double latitude, double longitude) {
		AirportData ad = new AirportData(iataCode, latitude, longitude);
		airportData.put(iataCode, ad);
		return ad;
	}

	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException {
		AirportData ad = findAirportData(iataCode);
		updateAtmosphericInformation(ad.getAtmosphericInfo(), pointType, dp);
	}

	private void updateAtmosphericInformation(AtmosphericInformation ai, String pointType, DataPoint dp)
			throws WeatherException {
		final DataPointType dptype = DataPointType.valueOf(pointType.toUpperCase());
		if (dptype == null) {
			throw new WeatherException("couldn't update atmospheric data");
		} else {
			boolean updated = ai.updateDataPoint(dptype, dp);
			if (!updated) {
				throw new WeatherException("couldn't update atmospheric data as the data is out of valid range");
			}
		}
	}

	public boolean isUpdatedInLastDay(AtmosphericInformation ai) {
		return ai.getLastUpdateTime() > System.currentTimeMillis() - Constants.SECONDS_IN_A_DAY;
	}

	public void clear() {
		airportData.clear();
	}

}
