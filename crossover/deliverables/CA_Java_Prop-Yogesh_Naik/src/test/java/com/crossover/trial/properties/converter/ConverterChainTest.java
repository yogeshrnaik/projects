package com.crossover.trial.properties.converter;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.crossover.trial.properties.converter.ConverterChain;

public class ConverterChainTest {

	private static final String DUMMY_VALUE = "XYZ";
	private static final String SCORE_FACTOR = "score.factor";
	private static final String JPA_SHOW_SQL = "jpa.showSql";
	private static final double DELTA = 0.00001;
	private static final ConverterChain converterChain = new ConverterChain();

	@Test
	public void booleanConversion() {
		boolean showSql = (boolean) converterChain.convert(JPA_SHOW_SQL, "false");
		Assert.assertFalse(showSql);

		showSql = (boolean) converterChain.convert(JPA_SHOW_SQL, "true");
		Assert.assertTrue(showSql);

		String strShowSql = (String) converterChain.convert(JPA_SHOW_SQL, DUMMY_VALUE);
		Assert.assertEquals(DUMMY_VALUE, strShowSql);
	}

	@Test
	public void doubleConversion() {
		double score = (double) converterChain.convert(SCORE_FACTOR, "2.4");
		Assert.assertEquals(2.4, score, DELTA);

		score = (Double) converterChain.convert(SCORE_FACTOR, "1.0");
		Assert.assertEquals(1, score, DELTA);

		String strScore = (String) converterChain.convert(SCORE_FACTOR, DUMMY_VALUE);
		Assert.assertEquals(DUMMY_VALUE, strScore);
	}

	@Test
	public void integerConversion() {
		int accountId = (int) converterChain.convert("aws_account_id", "123456");
		Assert.assertEquals(123456, accountId);

		String strAccountId = (String) converterChain.convert("aws_account_id", DUMMY_VALUE);
		Assert.assertEquals(DUMMY_VALUE, strAccountId);
	}

	@Test
	public void awsRegionConversion() {
		Regions awsRegion = (Regions) converterChain.convert("aws_region_id", "us-east-1");
		Assert.assertEquals(Regions.US_EAST_1, awsRegion);

		String strAccountId = (String) converterChain.convert("aws_region_id", DUMMY_VALUE);
		Assert.assertEquals(DUMMY_VALUE, strAccountId);
	}

	@Test
	public void nullValueConversion() {
		String nullValue = (String) converterChain.convert("password", null);
		Assert.assertNull(nullValue);
	}

}
