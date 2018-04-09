package com.tomtom.cleancode.exercise.productization;

import static com.tomtom.places.unicorn.matching.util.NamesUtil.getScaledLevenshteinDistance;

import com.google.common.base.Optional;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.source.NameType;
import com.tomtom.places.unicorn.matching.util.NamesUtil;

public abstract class NameLanguageMatcher {

    public static NameLanguageMatcher getNameLanguageMatcher(Configuration configuration, ArchiveName archiveName) {
        if (doAlternateNameExactComparison(configuration, archiveName)) {
            return new EqualsIgnoreCaseMatcher();
        } else {
            return new ScaledLevenshteinSimilarityMatcher(configuration.getRulesConfiguration()
                .getAlternateNameSimilarityComparisonPercentage());
        }
    }

    private static boolean doAlternateNameExactComparison(Configuration configuration, ArchiveName archiveName) {
        return configuration.getRulesConfiguration().doAlternateNameExactComparison(NamesUtil.toString(archiveName.getLanguage()));
    }

    public Optional<NameMatchResult>
        getNameMatch(NameType nameTypeFromPOI, ArchiveName archiveNameFromPOI, AlternateName nameToBeAdded) {
        if (languageEquals(archiveNameFromPOI, nameToBeAdded.getArchiveName())) {
            return nameEquals(nameTypeFromPOI, archiveNameFromPOI, nameToBeAdded);
        }
        return Optional.absent();
    }

    protected abstract Optional<NameMatchResult> nameEquals(NameType nameTypeFromPOI, ArchiveName archiveNameFromPOI,
        AlternateName nameToBeAdded);

    private boolean languageEquals(ArchiveName archiveNameFromPOI, ArchiveName archiveNameToBeAdded) {
        return NamesUtil.toString(archiveNameToBeAdded.getLanguage()).equals(NamesUtil.toString(archiveNameFromPOI.getLanguage()));
    }
}

class NameMatchResult {

    private final ArchiveName matchedName;
    private final NameType matchedNameType;
    private final AlternateName skippedName;
    private final Optional<Double> similarityScore;

    public NameMatchResult(ArchiveName matchedName, NameType matchedNameType, AlternateName skippedName, Optional<Double> similarityScore) {
        this.matchedName = matchedName;
        this.matchedNameType = matchedNameType;
        this.skippedName = skippedName;
        this.similarityScore = similarityScore;
    }

    public ArchiveName getMatchedName() {
        return matchedName;
    }

    public NameType getMatchedNameType() {
        return matchedNameType;
    }

    public String getSkippedName() {
        return NamesUtil.toString(skippedName.getArchiveName().getName());
    }

    public String getSkippedNameIso2Lang() {
        return skippedName.getNormalizedNameLanguage();
    }

    public String getSkippedNameScript() {
        return skippedName.getNormalizedNameScript();
    }

    public Optional<Double> getSimilarityScore() {
        return similarityScore;
    }

    public boolean isSimilarityMatch() {
        return similarityScore.isPresent();
    }

}

class EqualsIgnoreCaseMatcher extends NameLanguageMatcher {

    @Override
    protected Optional<NameMatchResult> nameEquals(NameType nameTypeFromPOI, ArchiveName archiveNameFromPOI, AlternateName nameToBeAdded) {
        if (NamesUtil.toString(archiveNameFromPOI.getName()).equalsIgnoreCase(nameToBeAdded.getName())) {
            return Optional.of(new NameMatchResult(archiveNameFromPOI, nameTypeFromPOI, nameToBeAdded, Optional.<Double>absent()));
        }
        return Optional.absent();
    }
}

class ScaledLevenshteinSimilarityMatcher extends NameLanguageMatcher {

    private final double scaledLevenshteinPercentageThreshold;

    public ScaledLevenshteinSimilarityMatcher(double similarityBenchmark) {
        scaledLevenshteinPercentageThreshold = similarityBenchmark;
    }

    @Override
    protected Optional<NameMatchResult> nameEquals(NameType nameTypeFromPOI, ArchiveName archiveNameFromPOI, AlternateName nameToBeAdded) {
        double scaledLevenshtein = getScaledLevenshteinDistance(archiveNameFromPOI.getName(), nameToBeAdded.getName());
        if (scaledLevenshtein >= scaledLevenshteinPercentageThreshold) {
            return Optional.of(new NameMatchResult(archiveNameFromPOI, nameTypeFromPOI, nameToBeAdded, Optional.of(scaledLevenshtein)));
        }
        return Optional.absent();
    }
}
