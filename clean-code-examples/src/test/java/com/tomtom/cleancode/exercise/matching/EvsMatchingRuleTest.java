package com.tomtom.cleancode.exercise.matching;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.domain.MatchSet;
import com.tomtom.places.unicorn.configuration.provider.rules.RulesConfiguration;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NAccuracy;
import com.tomtom.places.unicorn.domain.avro.normalized.NAddress;
import com.tomtom.places.unicorn.domain.avro.normalized.NBrand;
import com.tomtom.places.unicorn.domain.avro.normalized.NEvsStation;
import com.tomtom.places.unicorn.domain.avro.normalized.NHouse;
import com.tomtom.places.unicorn.domain.avro.normalized.NLocus;
import com.tomtom.places.unicorn.domain.avro.normalized.NPoint;
import com.tomtom.places.unicorn.domain.avro.normalized.NText;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy;
import com.tomtom.places.unicorn.domain.avro.source.LocationContentType;
import com.tomtom.places.unicorn.domain.util.SerializationUtil;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.matching.rule.MatchPlace;
import com.tomtom.places.unicorn.matching.rule.MatchStrength;
import com.tomtom.places.unicorn.traces.Tracer;

public class EvsMatchingRuleTest {

    private EvsMatchingRule rule;

    private static final String CITY_NAME_1 = "Gent";
    private static final String LANGUAGE_NL = "nl";
    private static final String STREET_NAME_1 = "Street name 1";
    private static final String someOtherStreet = "Street name 2";
    private static final String someOtherHouseNumber = "2";
    private static final String HOUSE_NUMBER_1 = "1";

    private MatchPlace place1;
    private MatchPlace place2;
    private Configuration configuration;
    private MatchSet matchSet;
    private Tracer tracer;

    @Before
    public void setup() throws IOException {
        rule = new EvsMatchingRule();
        configuration = mock(Configuration.class);
        matchSet = mock(MatchSet.class);
        tracer = mock(Tracer.class);

        Properties properties = new Properties();
        properties.setProperty("evs.threshold.distance", "75");
        when(configuration.getRulesConfiguration()).thenReturn(new RulesConfiguration(properties));

        CompositePlace compositePlace1 = buildDefaultCompositePlace();
        CompositePlace compositePlace2 = buildDefaultCompositePlace();

        place1 = new MatchPlace(compositePlace1);
        place2 = new MatchPlace(compositePlace2);

        compositePlace1.getNormalizedPlace().setEvsStation(Arrays.asList(new NEvsStation()));
        compositePlace2.getNormalizedPlace().setEvsStation(Arrays.asList(new NEvsStation()));

        // readRealData();

    }

    @Test
    @Ignore
    public void testRealData() {
        System.out.println(place1.getCompositePlace().getNormalizedPlace().getDeliveryPlaceId());
        System.out.println(place2.getCompositePlace().getNormalizedPlace().getDeliveryPlaceId());
        System.out.println(rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    private void readRealData() throws IOException {
        List<ClusteredPlace> clusteredPlaces = SerializationUtil.loadFromJsons(
            ClusteredPlace.SCHEMA$, "C:/Tanveer/avros/Newfolder/cluster");

        CompositePlace compositePlace1 = clusteredPlaces.get(0).getMatchingPlaces().get(0);
        CompositePlace compositePlace2 = clusteredPlaces.get(1).getMatchingPlaces().get(0);

        place1 = new MatchPlace(compositePlace1);
        place2 = new MatchPlace(compositePlace2);

    }

    private CompositePlace buildDefaultCompositePlace() {

        NormalizedPlace mappedPlace = buildDefaultNormalizedPlace();
        NormalizedPlace normalizedPlace = buildDefaultNormalizedPlace();

        return CompositePlace.newBuilder().setMappedPlace(mappedPlace).setNormalizedPlace(normalizedPlace).build();
    }

    private NormalizedPlace buildDefaultNormalizedPlace() {
        List<NBrand> brands = new ArrayList<NBrand>();
        NBrand nBrand = NBrand.newBuilder().setName(NText.newBuilder().setValue("ABC").build()).build();
        brands.add(nBrand);
        NormalizedPlace normalizedPlace = NormalizedPlace.newBuilder()
            .setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID()))
            .setLoci(new ArrayList<NLocus>())
            .setBrands(brands)
            .build();
        NLocus locus = buildDefaultDeliveredLocus();
        normalizedPlace.getLoci().add(locus);
        return normalizedPlace;
    }

    private NLocus buildDefaultDeliveredLocus() {
        return buildLocus(STREET_NAME_1, LANGUAGE_NL, CITY_NAME_1, LANGUAGE_NL, HOUSE_NUMBER_1, 45d, 12d);
    }

    private NLocus buildLocus(String streetName, String streetLanguage, String city, String cityLanguage, String houseNumber, Double lat,
        Double lon) {
        return NLocus.newBuilder().setLocationContentType(LocationContentType.AddressDelivered)
            .setAccuracy(NAccuracy.newBuilder().setGeocodingAccuracy(GeocodingAccuracy.AddressPoint).setPositionalAccuracy(null).build())
            .setPoint(NPoint.newBuilder().setLatitude(lat).setLongitude(lon).build())
            .setAddress(buildAddress(streetName, streetLanguage, city, cityLanguage, houseNumber))
            .setLocusToEpKey(UUIDUtil.randomAVUUID())
            .build();
    }

    private NAddress buildAddress(String streetName, String streetLanguage, String city, String cityLanguage, String houseNumber) {
        return NAddress.newBuilder()
            .setStreet(NText.newBuilder().setValue(streetName).setLanguage(streetLanguage).setScript(null).build())
            .setNormalizedStreet(NText.newBuilder().setValue(streetName).setLanguage(streetLanguage).setScript(null).build())
            .setCity(NText.newBuilder().setValue(city).setLanguage(cityLanguage).setScript(null).build())
            .setHouse(NHouse.newBuilder().setNumber(houseNumber).setBuilding(null).setSuite(null).setExtension(null).build())
            .build();
    }

    @Test
    public void testAttribute() {
        assertEquals("evsDeduplication", rule.getAttribute());
    }

    @Test
    public void testMatchingPlace() {
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldNotMatchFarDistantEVS() {
        place2.getNormalizedPlace().getLoci().get(0).getPoint().setLatitude(44d);
        assertEquals(MatchStrength.BAD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldNotMatchEVSWithDifferentBrands() {
        place2.getNormalizedPlace().getBrands().get(0).getName().setValue("XYZ");
        assertEquals(MatchStrength.BAD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSWithSomeUncommonBrand() {
        place2.getNormalizedPlace().getBrands().add(NBrand.newBuilder().setName(NText.newBuilder().setValue("XYZ").build()).build());
        assertEquals(MatchStrength.BAD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchIfEVSHasBrandsButOtherNot() {
        place2.getNormalizedPlace().getBrands().clear();
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSWithBothPlaceDontHaveBrands() {
        place1.getNormalizedPlace().getBrands().clear();
        place2.getNormalizedPlace().getBrands().clear();
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSWithExactSameBrands() {
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSIfBothHaveSameHouseNumber() {
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldNotMatchEVSWithDifferentHouseNumber() {
        place2.getCompositePlace().getMappedPlace().getLoci().get(0).getAddress().getHouse().setNumber(someOtherHouseNumber);
        place2.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAddress().getHouse().setNumber(someOtherHouseNumber);
        assertEquals(MatchStrength.NONE, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSIfOneEvsHasHouseNumberMissing() {
        place2.getCompositePlace().getMappedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        place2.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldMatchEVSIfBothEvsHasHouseNumberMissing() {
        place2.getCompositePlace().getMappedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        place2.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        place1.getCompositePlace().getMappedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        place1.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAddress().getHouse().setNumber(null);
        assertEquals(MatchStrength.GOOD, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldNotMatchEVSWithMissingStreet() {
        place2.getCompositePlace().getMappedPlace().getLoci().get(0).getAddress().getStreet().setValue(someOtherStreet);
        place2.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAddress().getStreet().setValue(someOtherStreet);
        assertEquals(MatchStrength.NONE, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

    @Test
    public void shouldNotMatchEVSIfAccuracyIsNotGood() {
        place2.getCompositePlace().getMappedPlace().getLoci().get(0).getAccuracy().setGeocodingAccuracy(GeocodingAccuracy.Area);
        place2.getCompositePlace().getNormalizedPlace().getLoci().get(0).getAccuracy().setGeocodingAccuracy(GeocodingAccuracy.Area);
        assertEquals(MatchStrength.NONE, rule.getMatch(matchSet, place1, place2, configuration, tracer));
    }

}
