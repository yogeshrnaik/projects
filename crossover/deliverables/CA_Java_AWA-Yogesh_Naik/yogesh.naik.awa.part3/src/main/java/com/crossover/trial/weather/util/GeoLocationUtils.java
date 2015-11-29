package com.crossover.trial.weather.util;

import com.crossover.trial.weather.model.Constants;
import com.crossover.trial.weather.model.GeoLocation;

public class GeoLocationUtils {

	/**
	 * Haversine distance between two Geo locations.
	 *
	 * @param geo1
	 * @param geo2
	 * @return the distance in KM
	 */
	public static double calculateDistance(GeoLocation geo1, GeoLocation geo2) {
		double deltaLat = Math.toRadians(geo2.getLatitude() - geo1.getLatitude());
		double deltaLon = Math.toRadians(geo2.getLongitude() - geo1.getLongitude());
		double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.pow(Math.sin(deltaLon / 2), 2) * Math.cos(geo1.getLatitude())
				* Math.cos(geo2.getLatitude());
		double c = 2 * Math.asin(Math.sqrt(a));
		return Constants.EARTH_RADIUS_IN_KM * c;
	}

	public static boolean isWithinRadius(GeoLocation geo1, GeoLocation geo2, double radius) {
		return calculateDistance(geo1, geo2) <= radius;
	}
}
