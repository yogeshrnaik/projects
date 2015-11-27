package com.crossover.trial.weather.model;

public class Airport {
	/** the three letter IATA code */
	private String iata;

	private GeoLocation location;

	public Airport(String iata, double latitude, double longitude) {
		this(iata, new GeoLocation(latitude, longitude));
	}

	public Airport(String iata, GeoLocation location) {
		this.iata = iata;
		this.location = location;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iata == null) ? 0 : iata.hashCode());
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
		Airport other = (Airport) obj;
		if (iata == null) {
			if (other.iata != null)
				return false;
		} else if (!iata.equals(other.iata))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Airport [iata=" + iata + ", location=" + location + "]";
	}
}
