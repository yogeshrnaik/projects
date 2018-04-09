package com.tomtom.cleancode.exercise.productization;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank.UseStage;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveBrandName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveStreetCity;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NName;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.NameType;
import com.tomtom.places.unicorn.domain.avro.trace.Phase;
import com.tomtom.places.unicorn.domain.avro.trace.TraceType;
import com.tomtom.places.unicorn.domain.normalized.NTexts;
import com.tomtom.places.unicorn.productization.gp3.rule.AbstractGP3BaseAttributeProductizationRule;
import com.tomtom.places.unicorn.traces.Tracer;

/**
 * Maps official names. Keep one official name per language. Add official name with language "UND" if no official name with other language
 * was added.
 */
public class OfficialNamesRule extends AbstractGP3BaseAttributeProductizationRule {

    private static final String UNDEFINED_LANGUAGE = "UND";

    private enum NameSource {
        FROM_BRANDS, FROM_LOCALITY
    }

    private static final Comparator<ArchiveName> NAME_COMPARATOR = new ArchiveNameComparator();

    private static final Map<String, NameSource> GENERATE_NAME_MAP = ImmutableMap.<String, NameSource>builder()
        .put("7310", NameSource.FROM_BRANDS) // Repair Facility
        .put("7311", NameSource.FROM_BRANDS) // Petrol Station
        .put("7312", NameSource.FROM_BRANDS) // Rent-a-Car Facility
        .put("7314", NameSource.FROM_BRANDS) // Hotel/Motel
        .put("7315", NameSource.FROM_BRANDS) // Restaurant
        .put("7327", NameSource.FROM_BRANDS) // Department Store
        .put("7373", NameSource.FROM_BRANDS) // Shopping Center
        .put("7388", NameSource.FROM_BRANDS) // Courier Dropbox
        .put("9361", NameSource.FROM_BRANDS) // Shop
        .put("9376", NameSource.FROM_BRANDS) // Cafe/Pub
        .put("7322", NameSource.FROM_LOCALITY) // Police Station
        .put("7324", NameSource.FROM_LOCALITY) // Post Office
        .put("7337", NameSource.FROM_LOCALITY) // Scenic/Panoramic View
        .build();

    public OfficialNamesRule(SupplierRanking supplierRanking) {
        super(supplierRanking);
    }

    @Override
    protected void applyFromBestPlace(ArchivePlace archivePlace, CompositePlace compositePlace, Configuration configuration, Tracer tracer) {

        SupplierRank rank = getSupplierRank(compositePlace.getNormalizedPlace());

        NormalizedPlace place = compositePlace.getNormalizedPlace();
        if (rank.getNamesUseStage() == UseStage.SOURCE || useOnlySourcePlace(compositePlace)) {
            place = compositePlace.getMappedPlace();
        }

        POI poi = getPoi(archivePlace);
        findNames(poi, place, NameType.Official);

        // Check for OfficialName. If there isn't one, generate it.
        if (poi.getOfficialNames().isEmpty()) {
            generateName(poi, tracer, place);
        }
        if (poi.getOfficialNames().isEmpty()) {
            // Promote alternate names
            findNames(poi, place, NameType.Alternative);
        }

        // Take only defined languages if possible
        attemptToTakeOnlyDefinedLanguages(poi.getOfficialNames());

        // always sort the official names to have a deterministic output
        Collections.sort(poi.getOfficialNames(), NAME_COMPARATOR);

        // Limit the number of official names to 1 per language
        limitToOnePerLanguage(poi.getOfficialNames(), place, tracer);
    }

    private void findNames(POI poi, NormalizedPlace place, NameType nameType) {
        if (place.getNames() != null) {
            for (NName name : place.getNames()) {
                if (name.getType().equals(nameType) && !NTexts.isEmpty(name.getText())) {
                    poi.getOfficialNames().add(ArchiveName.newBuilder()
                        .setName(name.getText().getValue())
                        .setLanguage(formatLanguage(name.getText()))
                        .build());
                }
            }
        }
    }

    /**
     * When no Official Name is found, generate it from locality (street + city), or from brandnames depending on the gdf feature code.
     *
     * @param poi POI
     * @param tracer to log that a name has been generated
     * @param normalizedPlace normalized place
     */
    private void generateName(POI poi, Tracer tracer, NormalizedPlace normalizedPlace) {
        // The GDF_FEATURE_CODE field in POI must be filled in, so this rule must run after ClassificationRule

        // TODO This used to be configurable in GP3. The hand-full of gdf codes that are
        // used to generate names are hard-coded in a map in this class. If we ever make
        // this configurable again, remove the map.

        if (poi.getGdfFeatureCode() != null) {
            String gdfCode = poi.getGdfFeatureCode().toString();
            if (GENERATE_NAME_MAP.containsKey(gdfCode)) {
                NameSource source = GENERATE_NAME_MAP.get(gdfCode);
                if (source.equals(NameSource.FROM_BRANDS)) {
                    generateNamesFromBrands(poi, tracer, normalizedPlace);
                } else if (source.equals(NameSource.FROM_LOCALITY)) {
                    generateNameFromLocality(poi, tracer, normalizedPlace);
                }
            }
        }
    }

    /**
     * Generate Official Name from street and/or city names
     *
     * @param poi POI
     * @param tracer to log that a name has been generated
     * @param normalizedPlace normalized place
     */
    private void generateNameFromLocality(POI poi, Tracer tracer, NormalizedPlace normalizedPlace) {

        if (!hasStreetAndCity(poi)) {
            // It's okay to have pois with no official name (GP3)
            // We might need to set official name and lang code to be empty.
            return;
        }

        ArchiveName generatedName = ArchiveName.newBuilder()
            .setName(getCityStreet(poi))
            .setLanguage(getStreetLanguageCode(poi))
            .build();
        poi.getOfficialNames().add(generatedName);

        tracer
            .warn(normalizedPlace.getDeliveryPlaceId(), Phase.Productizing, TraceType.OfficialNamesRule_GeneratedOfficialNameFromLocality)
            .param("generated name", generatedName.getName());
    }

    private boolean hasStreetAndCity(POI poi) {
        if (CollectionUtils.isNotEmpty(poi.getStreetsAndCities())) {
            ArchiveStreetCity first = poi.getStreetsAndCities().get(0);
            if (first.getStreet() != null && first.getStreet().length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate Official and Alternate names from the first four brandnames
     *
     * @param poi POI
     * @param tracer to log that a name has been generated
     * @param normalizedPlace normalized place
     */
    private void generateNamesFromBrands(POI poi, Tracer tracer, NormalizedPlace normalizedPlace) {

        // City-Street combination will be part of any generated name
        CharSequence cityStreetSuffix = getCityStreet(poi);

        // Language code for any new name will be the same.
        CharSequence newLanguageCode = getStreetLanguageCode(poi);

        StringBuilder newName = new StringBuilder();

        int index = 0;
        for (ArchiveBrandName brand : poi.getBrandNames()) {
            if (index == 4) {
                break;
            }
            if (brand.getValue().length() > 0) {
                newName.setLength(0);
                newName.append(brand.getValue());
                if (cityStreetSuffix.length() > 0) {
                    newName.append(" ");
                }
                newName.append(cityStreetSuffix);

                ArchiveName generatedName = ArchiveName.newBuilder()
                    .setName(newName.toString()).setLanguage(newLanguageCode).build();
                switch (index) {
                    case 0: {
                        // Build Official Name 1
                        poi.getOfficialNames().add(generatedName);
                        tracer.warn(normalizedPlace.getDeliveryPlaceId(), Phase.Productizing,
                            TraceType.OfficialNamesRule_GeneratedOfficialNameFromBrand)
                            .param("generated name", generatedName.getName());
                    }
                        break;
                    case 1:
                    case 2:
                    case 3: {
                        // Build Alternate Name 1, 2 or 3
                        if (!poi.getAlternateNames().contains(generatedName) &&
                            !poi.getOfficialNames().contains(generatedName)) {

                            poi.getAlternateNames().add(generatedName);
                            tracer
                                .warn(normalizedPlace.getDeliveryPlaceId(), Phase.Productizing,
                                    TraceType.OfficialNamesRule_GeneratedAlternateNameFromBrand)
                                .param("message", "Generated alternate name from brand")
                                .param("generated name", generatedName.getName());
                        }
                    }
                        break;
                    default:
                        break;
                }
            }
            ++index;
        }
    }

    /**
     * Append city and street name together, even if only one or the other is present.
     *
     * @param poi POI
     * @return "city street", "city", "street" or ""
     */
    private CharSequence getCityStreet(POI poi) {
        // City-Street combination will be part of any name
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(poi.getStreetsAndCities())) {
            CharSequence cityName = poi.getStreetsAndCities().get(0).getCity();
            CharSequence streetName = poi.getStreetsAndCities().get(0).getStreet();
            if (cityName != null) {
                sb.append(cityName.toString());
            }
            if (streetName != null) {
                if (sb.length() > 0 && streetName.length() > 0) {
                    sb.append(" ");
                }
                sb.append(streetName.toString());
            }
        }
        return sb.toString();
    }

    /**
     * Language code used for name generation. If no streetname language code, language code is undefined.
     *
     * @param poi POI
     * @return language code
     */
    private CharSequence getStreetLanguageCode(POI poi) {
        // If there is no street location language code, set it to undefined.
        StringBuilder sb = new StringBuilder();

        if (CollectionUtils.isNotEmpty(poi.getStreetsAndCities())) {
            CharSequence streetLangCode = poi.getStreetsAndCities().get(0).getStreetLanguage();
            if (streetLangCode != null && streetLangCode.length() > 0) {
                sb.append(streetLangCode);
            }
        }

        if (sb.length() == 0) {
            sb.append(UNDEFINED_LANGUAGE);
        }

        return sb.toString();
    }

    /**
     * If there are names with a language code that is not UND, remove any official name with language code UND.
     *
     * @param officialNames official names
     */
    private void attemptToTakeOnlyDefinedLanguages(List<ArchiveName> officialNames) {
        Predicate<ArchiveName> hasDefinedLanguage = new Predicate<ArchiveName>() {

            @Override
            public boolean apply(ArchiveName input) {
                return !input.getLanguage().toString().equals(UNDEFINED_LANGUAGE);
            }
        };
        if (Iterables.any(officialNames, hasDefinedLanguage)) {
            Iterables.removeIf(officialNames, Predicates.not(hasDefinedLanguage));
        }
    }

    private void limitToOnePerLanguage(List<ArchiveName> alternateNames, NormalizedPlace normalizedPlace, Tracer tracer) {

        Set<String> languageCounter = Sets.newHashSet();

        Iterator<ArchiveName> it = alternateNames.iterator();
        while (it.hasNext()) {
            ArchiveName archiveName = it.next();
            String language = archiveName.getLanguage().toString();
            if (languageCounter.contains(language)) {
                it.remove();
                tracer.warn(normalizedPlace.getDeliveryPlaceId(), Phase.Productizing, TraceType.LimitToOnePerLanguage).param("generated name", archiveName.getName());
            }
            languageCounter.add(language);
        }
    }

    @Override
    public String getDescription() {
        return "Maps official names.";
    }

}
