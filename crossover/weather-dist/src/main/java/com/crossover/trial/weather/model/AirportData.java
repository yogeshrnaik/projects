package com.crossover.trial.weather.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Contains basic airport information and atmospheric information related to that airport.
 *
 * @author code test administrator
 */
public class AirportData {

	private Airport airport;

	private AtmosphericInformation atmosphericInfo;

	public AirportData() {
		this(null, 0, 0);
	}

	public AirportData(String iata, double latitude, double longitude) {
		this(new Airport(iata, latitude, longitude), new AtmosphericInformation());
	}

	public AirportData(String iata, double latitude, double longitude, AtmosphericInformation atmosphericInfo) {
		this(new Airport(iata, latitude, longitude), atmosphericInfo);
	}

	public AirportData(Airport airport, AtmosphericInformation atmosphericInfo) {
		this.airport = airport;
		this.atmosphericInfo = atmosphericInfo;
	}

	public Airport getAirport() {
		return airport;
	}

	public GeoLocation getLocation() {
		return airport.getLocation();
	}

	public void setLocation(GeoLocation location) {
		airport.setLocation(location);
	}

	public AtmosphericInformation getAtmosphericInfo() {
		return atmosphericInfo;
	}

	public void setAtmosphericInfo(AtmosphericInformation atmosphericInfo) {
		this.atmosphericInfo = atmosphericInfo;
	}

	public String getIata() {
		return airport.getIata();
	}

	public void setIata(String iata) {
		airport.setIata(iata);
	}

	public double getLatitude() {
		return airport.getLocation().getLatitude();
	}

	public double getLongitude() {
		return airport.getLocation().getLongitude();
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airport == null) ? 0 : airport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirportData other = (AirportData) obj;
		if (airport == null) {
			if (other.airport != null)
				return false;
		} else if (!airport.equals(other.airport))
			return false;
		return true;
	}

}
