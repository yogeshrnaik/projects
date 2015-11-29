package com.crossover.trial.weather.ws;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.exception.WeatherException;
import com.crossover.trial.weather.model.AirportData;
import com.crossover.trial.weather.model.DataPoint;
import com.crossover.trial.weather.service.AirportService;
import com.google.gson.Gson;

/**
 * A REST implementation of the WeatherCollector API. Accessible only to airport weather collection sites via secure VPN.
 *
 * @author code test administrator
 */

@Path("/collect")
public class RestWeatherCollectorEndpoint implements WeatherCollector {
	public final static Logger LOGGER = Logger.getLogger(RestWeatherCollectorEndpoint.class.getName());

	@Inject
	private AirportService airportService;

	/**
	 * Default constructor required by framework.
	 */
	public RestWeatherCollectorEndpoint() {
	}

	/**
	 * This constructor is used in the Test class.
	 * 
	 * @param airportSrv
	 */
	public RestWeatherCollectorEndpoint(AirportService airportSrv) {
		this.airportService = airportSrv;
	}

	/** shared gson json to object factory */
	public final static Gson gson = new Gson();

	@GET
	@Path("/ping")
	@Override
	public Response ping() {
		return Response.status(Response.Status.OK).entity("ready").build();
	}

	@POST
	@Path("/weather/{iata}/{pointType}")
	@Override
	public Response updateWeather(@PathParam("iata") String iataCode, @PathParam("pointType") String pointType,
			String datapointJson) {
		try {
			DataPoint fromJson = gson.fromJson(datapointJson, DataPoint.class);
			airportService.addDataPoint(iataCode, pointType, fromJson);
		} catch (WeatherException e) {
			String error = String.format("Error adding new Data Point: [%s] for Data Point Type: [%s]", datapointJson,
					pointType);
			LOGGER.log(Level.WARNING, error, e);
			return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
		}
		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/airports")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAirports() {
		Set<String> retval = airportService.getAirports().stream().map(a -> a.getIata()).collect(Collectors.toSet());
		return Response.status(Response.Status.OK).entity(retval).build();
	}

	@GET
	@Path("/airport/{iata}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAirport(@PathParam("iata") String iata) {
		AirportData ad = airportService.findAirportData(iata);
		if (ad != null) {
			Map<String, Object> retval = new TreeMap<>();
			retval.put("iata", ad.getAirport().getIata());
			retval.put("latitude", ad.getLatitude());
			retval.put("longitude", ad.getLongitude());

			return Response.status(Response.Status.OK).entity(retval).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/airport/{iata}/{lat}/{long}")
	@Override
	public Response addAirport(@PathParam("iata") String iata, @PathParam("lat") String latString,
			@PathParam("long") String longString) {
		try {
			airportService.addAirport(iata, Double.valueOf(latString), Double.valueOf(longString));
			return Response.status(Response.Status.CREATED).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@DELETE
	@Path("/airport/{iata}")
	@Override
	public Response deleteAirport(@PathParam("iata") String iata) {
		AirportData ad = airportService.deleteAirport(iata);
		if (ad == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).build();
	}
}
