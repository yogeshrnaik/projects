package com.crossover.trial.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.crossover.trial.weather.model.Constants;

/**
 * A simple airport loader which reads a file from disk and sends entries to the webservice
 * 
 * @author code test administrator
 */
public class AirportLoader {
	public final static Logger LOGGER = Logger.getLogger(AirportLoader.class.getName());

	/** end point to supply updates */
	private WebTarget collect;

	public AirportLoader() {
		Client client = ClientBuilder.newClient();
		collect = client.target(Constants.BASE_URL + "/collect");
	}

	public static void main(String args[]) throws IOException {
		validate(args);
		upload(args[0]);
		System.exit(0);
	}

	private static void upload(String airportDataFile) throws IOException, FileNotFoundException {
		AirportLoader al = new AirportLoader();
		if (al.isServiceUp()) {
			try (FileInputStream ip = new FileInputStream(airportDataFile)) {
				al.upload(ip);
			}
		}
	}

	private static void validate(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("Usage: java com.crossover.trial.weather.util.AirportLoader <Path of airport file>");
			System.exit(1);
		}

		File airportDataFile = new File(args[0]);
		if (!airportDataFile.exists() || airportDataFile.length() == 0) {
			System.err.println(airportDataFile + " is not a valid input");
			System.exit(1);
		}
	}

	public void upload(InputStream airportDataStream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				processAirportLine(line);
			}
		}
	}

	private void processAirportLine(String line) {
		if (line != null && line.trim().length() > 0) {
			try {
				String[] data = line.split(",");
				String iata = data[4].replaceAll("\"", "");
				String latitude = Double.valueOf(data[6]).toString();
				String longitude = Double.valueOf(data[7]).toString();

				addAirport(iata, latitude, longitude);
			} catch (ProcessingException e) {
				LOGGER.log(Level.SEVERE, "Stopping the loader due to severe error:", e);
				System.exit(-1);
			} catch (Throwable e) {
				LOGGER.log(Level.WARNING, String.format("The line [%s] is not processed due to error: ", line), e);
			}
		}
	}

	private void addAirport(String iata, String latitude, String longitude) {
		String url = String.format("/airport/%s/%s/%s", iata, latitude, longitude);
		WebTarget path = collect.path(url);
		Response post = path.request().post(null);

		if (Response.Status.CREATED.getStatusCode() == post.getStatus()) {
			LOGGER.log(Level.INFO, String.format("Airport [%s, %s, %s] created successfully.", iata, latitude, longitude));
		} else {
			LOGGER.log(Level.WARNING, String.format("Service returned status code [%d] for [%s]", post.getStatus(), url));
		}
	}

	private boolean isServiceUp() {
		Response response = collect.path("/ping").request().get();
		return response.readEntity(String.class).equals("ready");
	}
}
