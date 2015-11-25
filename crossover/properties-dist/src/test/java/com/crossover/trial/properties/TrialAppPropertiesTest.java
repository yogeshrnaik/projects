package com.crossover.trial.properties;

import java.util.Arrays;

import org.junit.Test;

public class TrialAppPropertiesTest {

	@Test
	public void classpathResourceLoading() {
		TrialAppProperties props = new TrialAppProperties(
				Arrays.asList("classpath:resources/test_jdbc.properties",
						"file://D:/Yogesh/workspace/git_repo/projects/crossover/properties-dist/src/test/resources/test_aws.properties"));
		System.out.println(props.toString());

		System.out.println("\n***************** Missing props: *****************\n");
		System.out.println(props.getMissingProperties());
	}

}
