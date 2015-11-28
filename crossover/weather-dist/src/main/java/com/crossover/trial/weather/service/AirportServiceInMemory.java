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
 * Service that manages the airport and airport's atmospheric data in memory. Enum is used to create a singleton object
 * 
 * @author Yogesh Naik
 *
 */
@Service
public class AirportServiceInMemory implements AirportService {

	public AirportServiceInMemory() {
	}

	private static final Data data = Data.INSTANCE;

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
	public List<AtmosphericInformation> getAtmosphericInfoForNearByAirports(String iata, double radius) {
		AirportData source = findAirportData(iata);

		if (source != null) {
			return data.airportData.values().stream()
					.filter(a -> GeoLocationUtils.isWithinRadius(source.getLocation(), a.getLocation(), radius))
					.map(a -> a.getAtmosphericInfo()).filter(ai -> ai.hasReading()).collect(Collectors.toList());
		} else {
			return null;
		}
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
				throw new WeatherException(String.format("Atmospheric data is out of valid range: %d (inclusive) to %d (exclusive)",
						dptype.getMinMeanInclusive(), dptype.getMaxMeanExclusive()));
			}
		} catch (IllegalArgumentException e) {
			throw new WeatherException(String.format("%s is not a valid DataPointType", pointType));
		}

	}

	@Override
	public boolean isUpdatedInLastDay(AtmosphericInformation ai) {
		return ai.getLastUpdateTime() > System.currentTimeMillis() - Constants.SECONDS_IN_A_DAY;
	}
}
