package com.crossover.trial.weather;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

    /** the three letter IATA code */
    String iata;

    /** latitude value in degrees */
    double latitude;

    /** longitude value in degrees */
    double longitude;

    public AirportData() { }
    
    // CR: constructor using all the fields should be provided for convenience of creating an object of this class.
    // CR: with just the default constructor, client of this class has to call all three setters of different fields separately

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object other) {
        if (other instanceof AirportData) {
        	// CR: null check of the "other" parameter should be done to avoid NullPointerException
            return ((AirportData)other).getIata().equals(this.getIata());
        }

        return false;
    }
    
    // CR: implementation of hashCode() method should be provided as this class is used as Key in Map
    // CR: hashCode() method should use only iata field as it is the only field used by equals() method
}
