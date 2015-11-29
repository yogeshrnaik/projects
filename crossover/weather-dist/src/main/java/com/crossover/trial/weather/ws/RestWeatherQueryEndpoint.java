package com.crossover.trial.weather.ws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.AtmosphericInformation;
import com.crossover.trial.weather.service.AirportService;
import com.crossover.trial.weather.service.RequestStatsService;
import com.google.gson.Gson;

/**
 * The Weather App REST endpoint allows clients to query, update and check health stats. Currently, all data is held in
 * memory. The end point deploys to a single container
 *
 * @author code test administrator
 */
@Path("/query")
public class RestWeatherQueryEndpoint implements WeatherQueryEndpoint {

	public final static Logger LOGGER = Logger.getLogger("WeatherQuery");

	@Inject
	private AirportService airportService;

	@Inject
	private RequestStatsService reqStatsService;

	/**
	 * Default constructor required by framework.
	 */
	public RestWeatherQueryEndpoint() {
	}

	/**
	 * This constructor is used in the Test class.
	 * 
	 * @param airportSrv
	 */
	public RestWeatherQueryEndpoint(AirportService airportSrv, RequestStatsService reqStatsService) {
		this.airportService = airportSrv;
		this.reqStatsService = reqStatsService;
	}

	/** shared gson json to object factory */
	public static final Gson gson = new Gson();

	/**
	 * Retrieve service health including total size of valid data points and request frequency information.
	 *
	 * @return health stats for the service as a string
	 */
	@GET
	@Path("/ping")
	public String ping() {
		Map<String, Object> retval = new HashMap<>();

		retval.put("datasize", airportService.getCountOfAtmosphericInfoUpdatedInLastDay());
		retval.put("iata_freq", reqStatsService.getIataFrequencyStats());
		retval.put("radius_freq", reqStatsService.getRadiusFrequencyStats());

		return gson.toJson(retval);
	}

	/**
	 * Given a query in json format {'iata': CODE, 'radius': km} extracts the requested airport information and return a list
	 * of matching atmosphere information.
	 *
	 * @param iata
	 *            the iataCode
	 * @param radiusString
	 *            the radius in km
	 *
	 * @return a list of atmospheric information
	 */
	@GET
	@Path("/weather/{iata}/{radius}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("iata") String iata, @PathParam("radius") String radiusString) {
		double radius = radiusString == null || radiusString.trim().isEmpty() ? 0 : Double.valueOf(radiusString);
		reqStatsService.updateRequestFrequency(iata, radius);

		List<AtmosphericInformation> retval = airportService.getAtmosphericInformation(iata, radius);
		if (retval == null || retval.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.status(Response.Status.OK).entity(retval).build();
	}
}
