package com.tomtom.cleancode.exercise.matching;

import static com.google.common.collect.Sets.newHashSet;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.Area;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.Country;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.Failed;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.FailedDistanceTest;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.NotGeocoded;
import static com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy.PostalCode;
import static com.tomtom.places.unicorn.domain.util.CharSequenceUtil.asNonNullString;
import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.containsAny;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.domain.MatchSet;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NAccuracy;
import com.tomtom.places.unicorn.domain.avro.normalized.NAddress;
import com.tomtom.places.unicorn.domain.avro.normalized.NBrand;
import com.tomtom.places.unicorn.domain.avro.normalized.NHouse;
import com.tomtom.places.unicorn.domain.avro.normalized.NLocus;
import com.tomtom.places.unicorn.domain.avro.normalized.NText;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.GeocodingAccuracy;
import com.tomtom.places.unicorn.matching.rule.MatchPlace;
import com.tomtom.places.unicorn.matching.rule.MatchStrength;
import com.tomtom.places.unicorn.matching.ruleimpl.AbstractMatchingRule;
import com.tomtom.places.unicorn.matching.util.CoordinatesUtil;
import com.tomtom.places.unicorn.traces.Tracer;

/**
 * Matching rule to compare two EVS POIs
 */
public class EvsMatchingRule extends AbstractMatchingRule {

    private static final List<GeocodingAccuracy> badGeocodeResults = asList(Failed, FailedDistanceTest, NotGeocoded, Area, Country,
        PostalCode);

    @Override
    public MatchStrength getMatch(MatchSet matchSet, MatchPlace matchPlace1, MatchPlace matchPlace2, Configuration configuration,
        Tracer tracer) {

        NormalizedPlace normalizedPlace1 = matchPlace1.getNormalizedPlace();
        NormalizedPlace normalizedPlace2 = matchPlace2.getNormalizedPlace();

        if (hasEvsStation(normalizedPlace1) && hasEvsStation(normalizedPlace2)) {

            if (!addressMatch(matchPlace1, matchPlace2)) {
                return MatchStrength.NONE;
            }

            Double distance = CoordinatesUtil.getMinimumDistance(normalizedPlace1, normalizedPlace2);
            if (distance > configuration.getRulesConfiguration().getEvsThresholdDistance()) {
                return MatchStrength.BAD;
            } else {
                return getBrandNamesMatchStrength(normalizedPlace1, normalizedPlace2);
            }
        }

        return MatchStrength.NONE;
    }

    private boolean addressMatch(MatchPlace matchPlace1, MatchPlace matchPlace2) {

        final Set<String> houses1 = newHashSet();
        final Set<String> streets1 = newHashSet();

        final Set<String> houses2 = newHashSet();
        final Set<String> streets2 = newHashSet();

        populateHousesAndStreets(matchPlace1, houses1, streets1);
        populateHousesAndStreets(matchPlace2, houses2, streets2);

        if (isNotEmpty(houses1) && isNotEmpty(houses2)) {
            return containsAny(houses1, houses2) && containsAny(streets1, streets2);
        }

        return containsAny(streets1, streets2);

    }

    private void populateHousesAndStreets(MatchPlace matchPlace, Set<String> houses, Set<String> streets) {
        final CompositePlace compositePlace = matchPlace.getCompositePlace();
        populateHousesAndStreets(compositePlace.getMappedPlace().getLoci(), houses, streets);
        populateHousesAndStreets(compositePlace.getNormalizedPlace().getLoci(), houses, streets);
    }

    private void populateHousesAndStreets(List<NLocus> loci, Set<String> houses, Set<String> streets) {
        for (NLocus locus : loci) {
            final NAddress address = locus.getAddress();
            final NAccuracy accuracy = locus.getAccuracy();
            if (address != null && (accuracy == null || accuracy != null && !badGeocodeResults.contains(accuracy.getGeocodingAccuracy()))) {
                populateAddresses(houses, address.getHouse());
                populateAddresses(streets, address.getStreet());
            }
        }
    }

    private void populateAddresses(Set<String> houses, NHouse nHouse) {
        if (nHouse != null && isNotBlank(asNonNullString(nHouse.getNumber()))) {
            houses.add(nHouse.getNumber().toString().toUpperCase());
        }
    }

    private void populateAddresses(Set<String> streets, NText nText) {
        if (nText != null && isNotBlank(asNonNullString(nText.getValue()))) {
            streets.add(nText.getValue().toString().toUpperCase());
        }
    }

    private MatchStrength getBrandNamesMatchStrength(NormalizedPlace normalizedPlace1, NormalizedPlace normalizedPlace2) {
        Set<String> brandNames1 = getBrandNames(normalizedPlace1);
        Set<String> brandNames2 = getBrandNames(normalizedPlace2);

        boolean bothPlaceDontHaveBrands = brandNames1.size() == 0 && brandNames2.size() == 0;
        boolean onlyOnePlaceHasBrands = onePlaceHasBrands(brandNames1, brandNames2) || onePlaceHasBrands(brandNames2, brandNames1);
        boolean bothPlaceHaveSameBrands = brandNames1.containsAll(brandNames2) && brandNames2.containsAll(brandNames1);

        if (bothPlaceDontHaveBrands || onlyOnePlaceHasBrands || bothPlaceHaveSameBrands) {
            return MatchStrength.GOOD;
        }
        return MatchStrength.BAD;
    }

    private boolean onePlaceHasBrands(Set<String> brandNames1, Set<String> brandNames2) {
        return brandNames1.size() == 0 && brandNames2.size() > 0;
    }

    private Set<String> getBrandNames(NormalizedPlace normalizedPlace) {
        Set<String> brandNames = newHashSet();
        for (NBrand brand : normalizedPlace.getBrands()) {
            String brandName = asNonNullString(brand.getName());
            if (isNotBlank(brandName)) {
                brandNames.add(brandName);
            }
        }
        return brandNames;
    }

    private boolean hasEvsStation(NormalizedPlace place) {
        return CollectionUtils.isNotEmpty(place.getEvsStation());
    }

    @Override
    public String getAttribute() {
        return "evsDeduplication";
    }

}
