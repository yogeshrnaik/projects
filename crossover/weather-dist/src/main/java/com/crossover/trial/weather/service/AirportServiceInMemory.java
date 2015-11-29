package com.crossover.trial.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jvnet.hk2.annotations.Service;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.Airport;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.Constants;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.util.GeoLocationUtils;

/**
 * Service that manages the airport and airport's atmospheric data in memory.
 * 
 * @author Yogesh Naik
 *
 */
@Service
public class AirportServiceInMemory implements AirportService {

	public final static Logger LOGGER = Logger.getLogger(AirportServiceInMemory.class.getName());

	public AirportServiceInMemory() {
	}

	private static final Data data = Data.INSTANCE;

	/**
	 * Enum is used to create a singleton in memory data object
	 */
	private static enum Data {
		INSTANCE;

		/** airport data stored in ConcurrentHashMap to avoid concurrency issues */
		private Map<String, AirportData> airportData = new ConcurrentHashMap<>();

		private Data() {
			init();
		}

		/**
		 * Loads sample data from a file
		 */
		public void init() {
			clear();
			loadAirportsInMemory();
		}

		private void loadAirportsInMemory() {
			String line = null;
			final String airportsDataFile = "airports.dat";
			try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(airportsDataFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
				while ((line = br.readLine()) != null) {
					String[] split = line.split(",");
					AirportData ad = addAirport(split[0], Double.valueOf(split[1]), Double.valueOf(split[2]));
					LOGGER.log(Level.INFO,
							String.format("Airport [%s] loaded in memory from file [%s].", ad.getAirport(), airportsDataFile));
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING,
						String.format("Error while loading Airport's file [%s] in memory: ", airportsDataFile), e);
			}
		}

		public AirportData addAirport(String iataCode, double latitude, double longitude) {
			AirportData ad = new AirportData(iataCode, latitude, longitude);
			airportData.put(iataCode, ad);
			return ad;
		}

		public void clear() {
			airportData.clear();
		}

	}

	public void init() {
		data.init();
	}

	@Override
	public List<Airport> getAirports() {
		return getAirportData().stream().map(a -> a.getAirport()).collect(Collectors.toList());
	}

	@Override
	public AirportData findAirportData(String iataCode) {
		return data.airportData.get(iataCode);
	}

	@Override
	public List<AtmosphericInformation> getAtmosphericInformation() {
		return data.airportData.values().stream().map(a -> a.getAtmosphericInfo()).collect(Collectors.toList());
	}

	@Override
	public long getCountOfAtmosphericInfoUpdatedInLastDay() {
		return getAtmosphericInformation().stream().filter(ai -> ai.hasReading() && isUpdatedInLastDay(ai)).count();
	}

	@Override
	public List<AirportData> getAirportData() {
		return Collections.unmodifiableList(Arrays.asList(data.airportData.values().toArray(new AirportData[0])));
	}

	@Override
	public AirportData addAirport(String iataCode, double latitude, double longitude) {
		return data.addAirport(iataCode, latitude, longitude);
	}

	@Override
	public void addDataPoint(String iataCode, String pointType, DataPoint dp) throws WeatherException {
		AirportData ad = findAirportData(iataCode);
		if (ad == null) {
			throw new WeatherException(String.format("Airport not found: %s", iataCode));
		}
		updateAtmosphericInformation(ad.getAtmosphericInfo(), pointType, dp);
	}

	private void updateAtmosphericInformation(AtmosphericInformation ai, String pointType, DataPoint dp)
			throws WeatherException {
		try {
			final DataPointType dptype = DataPointType.valueOf(pointType.toUpperCase());
			boolean updated = ai.updateDataPoint(dptype, dp);
			if (!updated) {
				throw new WeatherException(String.format(
						"Atmospheric data is out of valid range: %d (inclusive) to %d (exclusive)",
						dptype.getMinMeanInclusive(), dptype.getMaxMeanExclusive()));
			}
		} catch (IllegalArgumentException e) {
			throw new WeatherException(String.format("%s is not a valid DataPointType", pointType));
		}

	}

	private boolean isUpdatedInLastDay(AtmosphericInformation ai) {
		return ai.getLastUpdateTime() > System.currentTimeMillis() - Constants.SECONDS_IN_A_DAY;
	}

	@Override
	public AirportData deleteAirport(String iata) {
		return data.airportData.remove(iata);
	}

	@Override
	public List<AtmosphericInformation> getAtmosphericInformation(String iata, double radius) {
		if (radius == 0) {
			return getAtmosphericInfoForAirport(iata);
		} else {
			return getAtmosphericInfoForNearbyAirports(iata, radius);
		}
	}

	private List<AtmosphericInformation> getAtmosphericInfoForAirport(String iata) {
		List<AtmosphericInformation> result = new ArrayList<>();

		AirportData airportData = findAirportData(iata);
		if (airportData != null) {
			result.add(airportData.getAtmosphericInfo());
		}

		return result;
	}

	private List<AtmosphericInformation> getAtmosphericInfoForNearbyAirports(String iata, double radius) {
		AirportData source = findAirportData(iata);

		if (source != null) {
			return data.airportData.values().stream()
					.filter(a -> GeoLocationUtils.isWithinRadius(source.getLocation(), a.getLocation(), radius))
					.map(a -> a.getAtmosphericInfo()).filter(ai -> ai.hasReading()).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

}
