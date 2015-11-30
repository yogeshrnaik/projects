package com.crossover.trial.properties.parser;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.crossover.trial.properties.converter.ConverterChain;
import com.crossover.trial.properties.exception.PropertyException;
import com.crossover.trial.properties.model.Key;
import com.crossover.trial.properties.model.Property;

public class JsonPropertiesParserTest {
	private static final PropertiesParser propsParser = new JsonPropertiesParser(new ConverterChain());

	private static final String jsonPropertiesString = "{\n" + 
			"\"auth.endpoint.uri\": \"https://authserver/v1/auth\",\n" + 
			"\"job.timeout\": 3600,\n" + 
			"\"job.maxretry\": 4,\n" + 
			"\"sns.broadcast.topic_name\": \"broadcast\",\n" + 
			"\"sns.broadcast.visibility_timeout\": 30,\n" + 
			"\"score.factor\": 2.4,\n" + 
			"\"jpa.showSql\": false\n" + 
		"}";

	@Test
	public void testParsePropertiesContents() {
		Map<Key, Property> props = propsParser.parse(jsonPropertiesString);

		Assert.assertEquals("https://authserver/v1/auth", props.get(new Key("auth.endPOINT_uri")).getTypeSafeValue());
		Assert.assertEquals(3600, props.get(new Key("job.timeout")).getTypeSafeValue());
		Assert.assertEquals("broadcast", props.get(new Key("sns.broadcast.topic_name")).getTypeSafeValue());
		Assert.assertEquals(2.4, props.get(new Key("score.factor")).getTypeSafeValue());
		Assert.assertFalse((boolean)props.get(new Key("jpa.showSql")).getTypeSafeValue());
	}

	@Test
	public void testParseEmptyContents() {
		Map<Key, Property> props = propsParser.parse(null);
		Assert.assertEquals(0, props.size());

		props = propsParser.parse("");
		Assert.assertEquals(0, props.size());
	}

	@Test(expected=PropertyException.class)
	public void testParseInvalidContents() {
		propsParser.parse("XYZ");
	}
}
