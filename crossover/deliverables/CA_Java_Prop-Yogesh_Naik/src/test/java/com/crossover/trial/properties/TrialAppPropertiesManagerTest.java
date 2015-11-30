package com.crossover.trial.properties;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;

public class TrialAppPropertiesManagerTest {

	private static final TrialAppPropertiesManager propsManager = new TrialAppPropertiesManager();

	@Test
	public void testLoadProperties() {
		AppProperties appProps = propsManager.loadProps(Arrays.asList("classpath:resources/test_aws.properties",
				"classpath:resources/test_jdbc.properties"));

		Assert.assertTrue(appProps.isValid());

		Assert.assertEquals(9, appProps.getKnownProperties().size());
		Assert.assertEquals("AKIAJSF6XRIJNJTTTL3Q", appProps.get("aws_access_key"));
		Assert.assertEquals("pmqnweEYvdiw7cvCdTOES48sOUvK1rGvvctBsgsa", appProps.get("aws_secret_key"));
		Assert.assertEquals(12345678, appProps.get("aws_account_id"));
		Assert.assertEquals(Regions.US_EAST_1, appProps.get("aws.region_id"));

		Assert.assertTrue((boolean) appProps.get("JpA_showSqL"));
	}

	@Test
	public void testLoadPropertiesWithInvalidFiles() {
		AppProperties appProps = propsManager.loadProps(Arrays.asList("classpath:resources/XYZ"));
		Assert.assertEquals(0, appProps.getKnownProperties().size());
	}
}
