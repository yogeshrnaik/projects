package com.tomtom.cleancode.exercise.productization;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.configuration.domain.Language;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank.UseStage;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NName;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.NameType;
import com.tomtom.places.unicorn.domain.avro.trace.Phase;
import com.tomtom.places.unicorn.domain.avro.trace.TraceType;
import com.tomtom.places.unicorn.productization.gp3.rule.AbstractGP3MergeAllAdditionalAttributesProductizationRule;
import com.tomtom.places.unicorn.productization.pipeline.CheckCharacterUtils;
import com.tomtom.places.unicorn.traces.TraceAttributes;
import com.tomtom.places.unicorn.traces.Tracer;

/**
 * Maps alternate names.
 */
public class AlternateNamesRule extends AbstractGP3MergeAllAdditionalAttributesProductizationRule {

    private static final int MAX_PER_LANGUAGE = 3;

    private static final Comparator<ArchiveName> NAME_COMPARATOR = new ArchiveNameComparator();

    public AlternateNamesRule(SupplierRanking supplierRanking) {
        super(supplierRanking);
    }

    @Override
    protected void applyFromPlace(ArchivePlace archivePlace, CompositePlace compositePlace, Configuration configuration, Tracer tracer) {
        NormalizedPlace place = getPlaceToProductize(compositePlace);
        if (CollectionUtils.isEmpty(place.getNames())) {
            return;
        }

        List<NName> validNames = filterNamesWithInvalidCharacters(archivePlace, place, configuration, tracer);
        if (CollectionUtils.isEmpty(validNames)) {
            return;
        }

        productizeAlternateNames(archivePlace, place, validNames, configuration, tracer);
    }

    private void productizeAlternateNames(ArchivePlace archivePlace, NormalizedPlace place, List<NName> validNames,
        Configuration configuration, Tracer tracer) {
        POI poi = getPoi(archivePlace);
        int existingANsCount = poi.getAlternateNames().size();
        addAlternateNames(configuration, place, validNames, poi, tracer);
        refineAlternateNames(place, archivePlace, configuration, poi, tracer);
        traceNewlyAddedAlternateNames(place, existingANsCount, poi, tracer);
    }

    private void addAlternateNames(Configuration config, NormalizedPlace place, List<NName> validNames, POI poi, Tracer tracer) {
        Set<String> officialLanguages = getOfficialLanguages(poi);

        for (NName name : validNames) {
            final AlternateName alternateName = toAlternateName(name);

            // don't add duplicates and only add alternate names for language that already occur in the official names
            if (officialLanguages.contains(alternateName.getArchiveNameLanguage())
                && !nameAlreadyExists(config, place, poi, alternateName, tracer)) {
                poi.getAlternateNames().add(alternateName.getArchiveName());
            }
        }
    }

    public AlternateName toAlternateName(NName name) {
        ArchiveName archiveName = archiveName(name);
        return new AlternateName(name, archiveName);
    }

    private List<NName> filterNamesWithInvalidCharacters(final ArchivePlace archivePlace, final NormalizedPlace place,
        final Configuration configuration, final Tracer tracer) {
        return Lists.newArrayList(Iterables.filter(place.getNames(), new Predicate<NName>() {
            @Override
            public boolean apply(NName name) {
                if (!CheckCharacterUtils.isValidName(archiveName(name), configuration, archivePlace)) {
                    traceInvalidNames(place, name, TraceType.NamesRule_InvalidCharactersInName, tracer);
                    return false;
                }
                if (CheckCharacterUtils.containsInvalidOccurrencesOfZero(name.getText().getValue().toString())) {
                    traceInvalidNames(place, name, TraceType.NamesRule_InvalidZeroInName, tracer);
                    return false;
                }
                return true;
            }
        }));
    }

    private void traceInvalidNames(NormalizedPlace place, NName name, TraceType traceType, Tracer tracer) {
        tracer.info(place.getDeliveryPlaceId().toString(), Phase.Productizing, traceType)
            .param("name type", name.getType())
            .param("skipped name", name.getText().getValue())
            .param("language", name.getText().getLanguage())
            .param("script", name.getText().getScript());
    }

    private void refineAlternateNames(NormalizedPlace place, ArchivePlace archivePlace, Configuration configuration,
        POI poi, Tracer tracer) {
        Collections.sort(poi.getAlternateNames(), NAME_COMPARATOR); // always sort to have a deterministic output
        limitToThreePerLanguage(poi.getAlternateNames());
    }

    private void traceNewlyAddedAlternateNames(NormalizedPlace place, int existingANsCount, POI poi, Tracer tracer) {
        List<ArchiveName> newlyAddedANs = getNewlyAddedAlternateNames(poi, existingANsCount);
        traceGeneratedAlternateNames(place, newlyAddedANs, tracer);
    }

    private List<ArchiveName> getNewlyAddedAlternateNames(POI poi, int existingANsCount) {
        return poi.getAlternateNames().subList(existingANsCount, poi.getAlternateNames().size());
    }

    private NormalizedPlace getPlaceToProductize(CompositePlace compositePlace) {
        SupplierRank rank = getSupplierRank(compositePlace.getNormalizedPlace());

        NormalizedPlace place = compositePlace.getNormalizedPlace();
        if (rank.getNamesUseStage() == UseStage.SOURCE || useOnlySourcePlace(compositePlace)) {
            place = compositePlace.getMappedPlace();
        }
        return place;
    }

    private ArchiveName archiveName(NName name) {
        return new ArchiveName(name.getText().getValue(), formatLanguage(name.getText()));
    }

    private Set<String> getOfficialLanguages(POI poi) {
        ImmutableSet.Builder<String> languages = ImmutableSet.builder();
        for (ArchiveName name : poi.getOfficialNames()) {
            languages.add(name.getLanguage().toString());
        }
        return languages.build();
    }

    private boolean nameAlreadyExists(Configuration configuration, NormalizedPlace place, POI poi, AlternateName toBeAddedAN,
        Tracer tracer) {
        Optional<NameMatchResult> matchedON =
            findArchiveNameByLanguageAndName(configuration, NameType.Official, poi.getOfficialNames(), toBeAddedAN);
        if (matchedON.isPresent()) {
            traceNameMatchResult(place, matchedON.get(), toBeAddedAN, tracer);
            return true;
        }

        Optional<NameMatchResult> matchedAN =
            findArchiveNameByLanguageAndName(configuration, NameType.Alternative, poi.getAlternateNames(), toBeAddedAN);
        if (matchedAN.isPresent()) {
            traceNameMatchResult(place, matchedAN.get(), toBeAddedAN, tracer);
            return true;
        }
        return false;
    }

    private void traceNameMatchResult(NormalizedPlace place, NameMatchResult nameMatchResult, AlternateName toBeAddedAN, Tracer tracer) {
        TraceType traceType = TraceType.AlternateNamesRule_SkippedEqualAlternateName;
        if (nameMatchResult.isSimilarityMatch()) {
            traceType = TraceType.AlternateNamesRule_SkippedSimilarAlternateName;
        }

        traceNameMatchAttributes(place, nameMatchResult, toBeAddedAN, traceType, tracer);

    }

    private void traceNameMatchAttributes(NormalizedPlace place, NameMatchResult nameMatchResult, AlternateName toBeAddedAN,
        TraceType traceType, Tracer tracer) {
        TraceAttributes traceAttr = tracer.info(place.getDeliveryPlaceId().toString(), Phase.Productizing, traceType)
            .param("skipped alternate name", toBeAddedAN.getArchiveName().getName())
            .param("language", toBeAddedAN.getNormalizedNameLanguage())
            .param("script", toBeAddedAN.getNormalizedNameScript())
            .param("similar name", nameMatchResult.getMatchedName().getName())
            .param("similar name type", nameMatchResult.getMatchedNameType());

        if (nameMatchResult.isSimilarityMatch()) {
            traceAttr.param("similarity score", nameMatchResult.getSimilarityScore().get());
        }
    }

    private Optional<NameMatchResult> findArchiveNameByLanguageAndName(Configuration configuration, NameType nameType,
        final List<ArchiveName> namesFromPOI, AlternateName nameToBeAdded) {
        NameLanguageMatcher matcher = NameLanguageMatcher.getNameLanguageMatcher(configuration, nameToBeAdded.getArchiveName());

        for (ArchiveName nameInPOI : namesFromPOI) {
            Optional<NameMatchResult> nameMatch = matcher.getNameMatch(nameType, nameInPOI, nameToBeAdded);
            if (nameMatch.isPresent()) {
                return nameMatch;
            }
        }
        return Optional.absent();
    }

    /**
     * Limit the number of alternate names to 3 per language
     */
    private void limitToThreePerLanguage(List<ArchiveName> alternateNames) {
        Multiset<String> languageCounter = HashMultiset.create();
        Iterator<ArchiveName> itr = alternateNames.iterator();
        while (itr.hasNext()) {
            ArchiveName archiveName = itr.next();
            String language = archiveName.getLanguage().toString();
            languageCounter.add(language);
            if (languageCounter.count(language) > MAX_PER_LANGUAGE) {
                itr.remove();
            }
        }
    }

    private void traceGeneratedAlternateNames(NormalizedPlace place, List<ArchiveName> alternateNames, Tracer tracer) {
        if (CollectionUtils.isEmpty(alternateNames)) {
            return;
        }

        TraceAttributes traceAttr =
            tracer.info(place.getDeliveryPlaceId().toString(), Phase.Productizing, TraceType.AlternateNamesRule_GeneratedAlternateName);

        int counterAN = 1;
        for (ArchiveName alternateName : alternateNames) {
            traceAlternateNameAttributes(counterAN++, alternateName, traceAttr);
        }
    }

    private void traceAlternateNameAttributes(int counterAN, ArchiveName alternateName, TraceAttributes traceAttr) {
        Language isoLang = RECODER.tifToIsoLanguage(alternateName.getLanguage().toString());
        traceAttr.param("alternate name " + counterAN, alternateName.getName())
            .param("language " + counterAN, isoLang.getLanguage())
            .param("script " + counterAN, isoLang.getScript());
    }

    @Override
    public String getDescription() {
        return "Maps alternate names.";
    }
}
