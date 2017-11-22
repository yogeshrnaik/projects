package com.raisin.challenge;

import org.apache.log4j.Logger;

import com.raisin.challenge.util.PropertyFileReader;

public class RaisinSolution {

    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        LOGGER.info(PropertyFileReader.getPropertyValue("source.a.url"));
    }
}
