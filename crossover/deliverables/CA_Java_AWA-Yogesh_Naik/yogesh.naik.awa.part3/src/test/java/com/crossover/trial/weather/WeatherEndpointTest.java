package com.crossover.trial.weather;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.model.DataPointType;
import com.crossover.trial.weather.service.AirportService;
import com.crossover.trial.weather.service.AirportServiceInMemory;
import com.crossover.trial.weather.service.RequestStatsServiceInMemory;
import com.crossover.trial.weather.ws.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.ws.RestWeatherQueryEndpoint;
import com.crossover.trial.weather.ws.WeatherCollector;
import com.crossover.trial.weather.ws.WeatherQueryEndpoint;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class WeatherEndpointTest {

	private AirportService airportService = new AirportServiceInMemory();
	private RequestStatsServiceInMemory reqStatsSrv = new RequestStatsServiceInMemory(airportService);
	private WeatherQueryEndpoint _query = new RestWeatherQueryEndpoint(airportService, reqStatsSrv);
	private WeatherCollector _update = new RestWeatherCollectorEndpoint(airportService);

	private Gson _gson = new Gson();

	private DataPoint _dp;

	@Before
	public void setUp() throws Exception {
		airportService.init();

		_dp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22).build();
		_update.updateWeather("BOS", "wind", _gson.toJson(_dp));
		_query.get("BOS", "0").getEntity();
	}

	@Test
	public void testPing() throws Exception {
		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());
		assertEquals(5, pingResult.getAsJsonObject().get("iata_freq").getAsJsonObject().entrySet().size());
	}

	@Test
	public void testGet() throws Exception {
		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("BOS", "0").getEntity();
		assertEquals(ais.get(0).getWind(), _dp);
	}

	@Test
	public void testGetForUnknownAirport() throws Exception {
		Response response = _query.get("MUM", "0");
		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) response.getEntity();
		assertEquals(null, ais);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetNearby() throws Exception {
		// check datasize response
		_update.updateWeather("JFK", "wind", _gson.toJson(_dp));
		_dp.setMean(40);
		_update.updateWeather("EWR", "wind", _gson.toJson(_dp));
		_dp.setMean(30);
		_update.updateWeather("LGA", "wind", _gson.toJson(_dp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("JFK", "200").getEntity();
		assertEquals(3, ais.size());
	}

	@Test
	public void testUpdateWeatherWithIncorrectValues() {
		int valueLessThanAllowed = DataPointType.HUMIDITY.getMinMeanInclusive() - 10;
		DataPoint dp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(valueLessThanAllowed).withLast(30)
				.withMean(20).build();
		Response post = _update.updateWeather("BOS", "humidity", _gson.toJson(dp));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), post.getStatus());

		int valueGreaterThanAllowed = DataPointType.HUMIDITY.getMaxMeanExclusive() + 10;
		dp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(valueGreaterThanAllowed).withLast(30)
				.withMean(20).build();
		post = _update.updateWeather("BOS", "humidity", _gson.toJson(dp));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), post.getStatus());
		
		post = _update.updateWeather("BOS", "xyz", _gson.toJson(dp));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), post.getStatus());
	}

	@Test
	public void testUpdate() throws Exception {

		DataPoint windDp = new DataPoint.Builder().withCount(10).withFirst(10).withMedian(20).withLast(30).withMean(22)
				.build();
		_update.updateWeather("BOS", "wind", _gson.toJson(windDp));
		_query.get("BOS", "0").getEntity();

		String ping = _query.ping();
		JsonElement pingResult = new JsonParser().parse(ping);
		assertEquals(1, pingResult.getAsJsonObject().get("datasize").getAsInt());

		DataPoint cloudCoverDp = new DataPoint.Builder().withCount(4).withFirst(10).withMedian(60).withLast(100)
				.withMean(50).build();
		_update.updateWeather("BOS", "cloudcover", _gson.toJson(cloudCoverDp));

		List<AtmosphericInformation> ais = (List<AtmosphericInformation>) _query.get("BOS", "0").getEntity();
		assertEquals(ais.get(0).getWind(), windDp);
		assertEquals(ais.get(0).getCloudCover(), cloudCoverDp);
	}

	@Test
	public void testWeatherCollectorPing() throws Exception {
		Response ping = _update.ping();
		assertEquals(Response.Status.OK.getStatusCode(), ping.getStatus());
	}

	@Test
	public void testGetAirports() throws Exception {
		Set<String> airports = (Set<String>) _update.getAirports().getEntity();
		assertEquals(5, airports.size());
	}

	@Test
	public void testGetAirport() throws Exception {
		testGetAirport("EWR", 40.6925, -74.168667);
	}

	@Test
	public void testGetAirportNotFound() throws Exception {
		testGetAirportNotFound("MUM");
	}

	private void testGetAirportNotFound(String iata) {
		Response post = _update.getAirport(iata);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), post.getStatus());
	}

	@Test
	public void testAddAirport() throws Exception {
		Response post = _update.addAirport("MUM", "19.0886", "72.8681");
		assertEquals(Response.Status.CREATED.getStatusCode(), post.getStatus());
		testGetAirport("MUM", 19.0886, 72.8681);
	}

	@Test
	public void testDeleteAirport() throws Exception {
		Response post = _update.deleteAirport("EWR");
		assertEquals(Response.Status.OK.getStatusCode(), post.getStatus());
		testGetAirportNotFound("EWR");
	}

	@Test
	public void testDeleteAirportNotFound() throws Exception {
		Response post = _update.deleteAirport("XYZ");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), post.getStatus());
	}

	@Test
	public void testAddAirportWithIncorrectLatitudeOrLongitudeValues() throws Exception {
		Response post = _update.addAirport("MUM", "XXX", "YYY");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), post.getStatus());
	}

	private void testGetAirport(String iata, double latitude, double longitude) {
		Map<String, Object> airport = (Map<String, Object>) _update.getAirport(iata).getEntity();
		assertEquals(3, airport.size());
		assertEquals(iata, airport.get("iata"));
		assertEquals(latitude, airport.get("latitude"));
		assertEquals(longitude, airport.get("longitude"));
	}
}