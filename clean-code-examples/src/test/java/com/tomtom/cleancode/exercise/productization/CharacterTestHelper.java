package com.tomtom.cleancode.exercise.productization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.HashMultimap;
import com.tomtom.places.unicorn.configuration.domain.AlphaRegexCountryMapping;


public class CharacterTestHelper {

    
    public static AlphaRegexCountryMapping createMapping(String LOCID){
        AlphaRegexCountryMapping dummy = new AlphaRegexCountryMapping();
        Map<String, Pattern> alphabetRegexMap = new HashMap<String, Pattern>();
        HashMultimap<String, Pattern> countryRegexMap = HashMultimap.create();
        String regexForLatin =
            "^[\u0020-\u002f\u0030-\u0031\u003a-\u003b\u003d\u003f-\u0040\u005e\u007c\u007e\u00b7\u00a1\u00b0\u00bf\\\\\u0030-\u0039\u0041-\u005a\u0060-\u007a\u00c0-\u017f\u0218\u0219\u021a\u021b\u01a0-\u01a1\u01af-\u01b0\u1ea0-\u1ef9]+$";
        String regexForHbr =
            "^[\u0020-\u002f\u0030-\u0031\u003a-\u003b\u003d\u003f-\u0040\u005e\u007c\u007e\u00b7\u00a1\u00b0\u00bf\\\\\u0030-\u0039\u0041-\u005a\u0060-\u007a\u05d0-\u05ea]+$";

        Pattern pattern = Pattern.compile(regexForLatin);
        List<Pattern> list = new ArrayList<Pattern>();
        list.add(pattern);

        countryRegexMap.put(LOCID, pattern);
        alphabetRegexMap.put("1", pattern);
        countryRegexMap.put(LOCID, Pattern.compile(regexForHbr));
        alphabetRegexMap.put("5", Pattern.compile(regexForHbr));
        dummy.setAlphabetRegexMap(alphabetRegexMap);
        dummy.setCountryRegexMap(countryRegexMap);
        
        return dummy;
    }
}
