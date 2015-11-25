package com.crossover.trial.properties;

import java.util.Arrays;

import org.junit.Test;

public class TrialAppPropertiesTest {

	@Test
	public void classpathResourceLoading() {
		TrialAppProperties props = new TrialAppProperties(Arrays.asList("classpath:resources/jdbc.properties",
				"file://D:/Yogesh/workspace/git_repo/projects/crossover/properties-dist/src/main/resources/aws.properties"));
		System.out.println(props.toString());

		System.out.println("\n***************** Missing props: *****************\n");
		System.out.println(props.getMissingProperties());

	}

}
