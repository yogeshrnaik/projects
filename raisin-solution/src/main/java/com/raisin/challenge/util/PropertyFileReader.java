package com.raisin.challenge.util;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;

public class PropertyFileReader {

    private static Properties properties = readApplicationProperties();

    public static String getPropertyValue(String property) {
        return properties.getProperty(property);
    }

    public static int getPropertyIntgerValue(String property) {
        return Integer.parseInt(properties.getProperty(property));
    }

    public static List<String> getPropertyValues(String property) {
        String value = properties.getProperty(property);
        return value != null ? Arrays.asList(value.split(",")) : Lists.<String>newArrayList();
    }

    private static Properties readApplicationProperties() {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        try {
            return (Properties)context.getBean("appProperties");
        } finally {
            context.close();
        }
    }
}
