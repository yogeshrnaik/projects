package com.crossover.trial.properties.parser;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.regions.Regions;
import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;

public class PropertiesParserTest {
	private static final PropertiesParser propsParser = new PropertiesParser(new ConverterChain());

	private static final String propertiesString = "aws_access_key = AKIAJSF6XRIJNJTTTL3Q\n"
														+ "aws_secret_key = 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\n" 
														+ "aws_account_id = 12345678\n"
														+ "aws_region_id = us-east-1";

	@Test
	public void testParsePropertiesContents() {
		Map<Key, Property> props = propsParser.parse(propertiesString);

		Assert.assertEquals("AKIAJSF6XRIJNJTTTL3Q", props.get(new Key("aws_access_key")).getTypeSafeValue());
		Assert.assertEquals("pmqnweEYvdiw7cvCdTOES48sOUvK1rGvvctBsgsa", props.get(new Key("aws_secret_key"))
				.getTypeSafeValue());
		Assert.assertEquals(12345678, props.get(new Key("aws_account_id")).getTypeSafeValue());
		Assert.assertEquals(Regions.US_EAST_1, props.get(new Key("aws.region_id")).getTypeSafeValue());
	}

	@Test
	public void testParseEmptyContents() {
		Map<Key, Property> props = propsParser.parse(null);
		Assert.assertEquals(0, props.size());

		props = propsParser.parse("");
		Assert.assertEquals(0, props.size());

		props = propsParser.parse("#comment");
		Assert.assertEquals(0, props.size());
	}
}
