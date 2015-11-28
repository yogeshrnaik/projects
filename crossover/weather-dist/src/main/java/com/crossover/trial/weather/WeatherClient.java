package com.crossover.trial.weather;

import static com.crossover.trial.weather.model.Constants.BASE_URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.Airport;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.service.AirportService;
import com.crossover.trial.weather.service.AirportServiceInMemory;

/**
 * A reference implementation for the weather client. Consumers of the REST API can look at WeatherClient to understand API
 * semantics. This existing client populates the REST endpoint with dummy data useful for testing.
 *
 * @author code test administrator
 */
public class WeatherClient {

	private AirportService airportService = new AirportServiceInMemory();

	/** end point for read queries */
	private WebTarget query;

	/** end point to supply updates */
	private WebTarget collect;

	public WeatherClient() {
		Client client = ClientBuilder.newClient();
		query = client.target(BASE_URL + "/query");
		collect = client.target(BASE_URL + "/collect");
	}

	public void pingCollect() {
		WebTarget path = collect.path("/ping");
		Response response = path.request().get();
		System.out.print("collect.ping: " + response.readEntity(String.class) + "\n");
	}

	public void pingQuery() {
		WebTarget path = query.path("/ping");
		Response response = path.request().get();
		System.out.println("query.ping: " + response.readEntity(String.class));
	}

	public void populate(String iata, String pointType, DataPoint dp) {
		WebTarget path = collect.path(String.format("/weather/%s/%s", iata, pointType));
		Response post = path.request().post(Entity.entity(dp, "application/json"));
	}

	public void query(String iata, int radius) {
		WebTarget path = query.path(String.format("/weather/%s/%d", iata, radius));
		Response response = path.request().get();
		System.out.println("query.get:" + response.readEntity(String.class));
	}

	public static void main(String[] args) {
		WeatherClient wc = new WeatherClient();
		wc.pingCollect();

		wc.populateDataPoints();
		wc.queryDataPoints();

		wc.pingQuery();
		System.out.print("complete");
		System.exit(0);
	}

	private void queryDataPoints() {
		for (Airport a : airportService.getAirports()) {
			query(a.getIata(), 0);
		}
	}

	/**
	 * populate sample data points of all Types for all airports
	 */
	private void populateDataPoints() {
		int counter = 0;
		for (Airport a : airportService.getAirports()) {
			for (DataPointType dpt : DataPointType.values()) {
				// calculate a base value of a particular DataPointType so that the calculated value is within the range
				// for WIND, special calculation is required as the maxMean value = Integer.MAX_INT
				// for other DataPointTypes, simply take the average of minMean and maxMean
				int baseVal = (dpt.equals(DataPointType.WIND)) ? counter + 10 : counter
						+ (dpt.getMinMeanInclusive() + dpt.getMaxMeanExclusive()) / 2;

				populate(a.getIata(), dpt.name().toLowerCase(),
						new DataPoint.Builder().withFirst(baseVal++)
						.withLast(baseVal++)
						.withMean(baseVal++)
						.withMedian(baseVal++)
						.withCount(baseVal++)
						.build());
			}
			counter++;
		}
	}
}
