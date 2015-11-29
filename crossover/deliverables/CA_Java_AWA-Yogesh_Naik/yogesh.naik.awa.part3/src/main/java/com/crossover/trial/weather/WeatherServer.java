package com.crossover.trial.weather;

import static com.crossover.trial.weather.model.Constants.BASE_URL;
import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.crossover.trial.weather.service.AirportService;
import com.crossover.trial.weather.service.AirportServiceInMemory;
import com.crossover.trial.weather.service.RequestStatsService;
import com.crossover.trial.weather.service.RequestStatsServiceInMemory;
import com.crossover.trial.weather.ws.RestWeatherCollectorEndpoint;
import com.crossover.trial.weather.ws.RestWeatherQueryEndpoint;

/**
 * A main method used to test the Weather Application locally -- live deployment is to a tomcat container.
 *
 * @author code test administrator
 */
public class WeatherServer {

	private HttpServer httpServer;

	public WeatherServer() {
		init();
	}

	public static void main(String[] args) {
		try {
			System.out.println("Starting Weather App local testing server: " + BASE_URL);
			System.out.println("Not for production use");

			WeatherServer weatherServer = new WeatherServer();
			weatherServer.start();

			System.out.println(format("Weather Server started.\n url=%s\n", BASE_URL));
			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(WeatherServer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void start() throws IOException {
		if (httpServer != null)
			httpServer.start();
	}

	private void init() {
		final ResourceConfig resourceConfig = registerEndPoints();
		httpServer = configHttpServer(resourceConfig);
	}

	private HttpServer configHttpServer(final ResourceConfig resourceConfig) {
		final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URL), resourceConfig, false);
		addShutdownHook(server);
		addProbes(server);
		return server;
	}

	private void addProbes(final HttpServer server) {
		HttpServerProbe probe = new HttpServerProbe.Adapter() {
			public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
				System.out.println(request.getRequestURI());
			}
		};

		server.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(probe);
	}

	private void addShutdownHook(final HttpServer server) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				server.shutdownNow();
			}
		}));
	}

	private ResourceConfig registerEndPoints() {
		final ResourceConfig resourceConfig = new ResourceConfig();

		resourceConfig.register(new AppBinder());
		resourceConfig.packages(true, "com.crossover.trial.weather");

		resourceConfig.register(RestWeatherCollectorEndpoint.class);
		resourceConfig.register(RestWeatherQueryEndpoint.class);
		return resourceConfig;
	}

	public static class AppBinder extends AbstractBinder {
		@Override
		protected void configure() {
			bind(AirportServiceInMemory.class).to(AirportService.class);
			bind(RequestStatsServiceInMemory.class).to(RequestStatsService.class);
		}
	}
}
