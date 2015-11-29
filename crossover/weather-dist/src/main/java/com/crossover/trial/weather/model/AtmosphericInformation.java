package com.crossover.trial.weather.model;

import java.util.HashMap;
import java.util.Map;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation {

	private Map<DataPointType, DataPoint> dataPoints = new HashMap<DataPointType, DataPoint>();

	/** the last time this data was updated, in milliseconds since UTC epoch */
	private long lastUpdateTime;

	public AtmosphericInformation() {
	}

	protected AtmosphericInformation(DataPoint temperature, DataPoint wind, DataPoint humidity, DataPoint percipitation,
			DataPoint pressure, DataPoint cloudCover) {
		dataPoints.put(DataPointType.TEMPERATURE, temperature);
		dataPoints.put(DataPointType.WIND, wind);
		dataPoints.put(DataPointType.HUMIDITY, humidity);
		dataPoints.put(DataPointType.PERCIPITATION, percipitation);
		dataPoints.put(DataPointType.PRESSURE, pressure);
		dataPoints.put(DataPointType.CLOUDCOVER, cloudCover);

		this.lastUpdateTime = System.currentTimeMillis();
	}

	public boolean hasReading() {
		return getCloudCover() != null || getHumidity() != null || getPressure() != null || getPercipitation() != null
				|| getTemperature() != null || getWind() != null;
	}

	public boolean updateDataPoint(DataPointType dataPointType, DataPoint dataPoint) {
		if (dataPointType.isValid(dataPoint.getMean())) {
			dataPoints.put(dataPointType, dataPoint);
			lastUpdateTime = System.currentTimeMillis();
			return true;
		}
		return false;
	}

	public DataPoint getTemperature() {
		return dataPoints.get(DataPointType.TEMPERATURE);
	}

	public void setTemperature(DataPoint temperature) {
		dataPoints.put(DataPointType.TEMPERATURE, temperature);
	}

	public DataPoint getWind() {
		return dataPoints.get(DataPointType.WIND);
	}

	public void setWind(DataPoint wind) {
		dataPoints.put(DataPointType.WIND, wind);
	}

	public DataPoint getHumidity() {
		return dataPoints.get(DataPointType.HUMIDITY);
	}

	public void setHumidity(DataPoint humidity) {
		dataPoints.put(DataPointType.HUMIDITY, humidity);
	}

	public DataPoint getPercipitation() {
		return dataPoints.get(DataPointType.PERCIPITATION);
	}

	public void setPrecipitation(DataPoint percipitation) {
		dataPoints.put(DataPointType.PERCIPITATION, percipitation);
	}

	public DataPoint getPressure() {
		return dataPoints.get(DataPointType.PRESSURE);
	}

	public void setPressure(DataPoint pressure) {
		dataPoints.put(DataPointType.PRESSURE, pressure);
	}

	public DataPoint getCloudCover() {
		return dataPoints.get(DataPointType.CLOUDCOVER);
	}

	public void setCloudCover(DataPoint cloudCover) {
		dataPoints.put(DataPointType.CLOUDCOVER, cloudCover);
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
