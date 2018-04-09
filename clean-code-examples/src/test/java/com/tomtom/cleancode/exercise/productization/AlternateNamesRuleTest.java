package com.tomtom.cleancode.exercise.productization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.configuration.domain.AlphaRegexCountryMapping;
import com.tomtom.places.unicorn.configuration.domain.Language;
import com.tomtom.places.unicorn.configuration.domain.LanguageRecoding;
import com.tomtom.places.unicorn.configuration.domain.Locality;
import com.tomtom.places.unicorn.configuration.domain.Locality.LocalityName;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank.UseStage;
import com.tomtom.places.unicorn.configuration.provider.rules.RulesConfiguration;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NName;
import com.tomtom.places.unicorn.domain.avro.normalized.NText;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.productized.ProductizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.AVUUID;
import com.tomtom.places.unicorn.domain.avro.source.NameType;
import com.tomtom.places.unicorn.domain.avro.source.ProcessingFlag;
import com.tomtom.places.unicorn.domain.avro.source.Supplier;
import com.tomtom.places.unicorn.domain.avro.trace.Phase;
import com.tomtom.places.unicorn.domain.avro.trace.TraceType;
import com.tomtom.places.unicorn.domain.normalized.NCategories;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.productization.gp3.GP3Utils;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class AlternateNamesRuleTest {

    private static final LanguageRecoding RECODER = LanguageRecoding.getInstance();

    private static final String BEL = "BEL";

    private AlternateNamesRule alternateNamesRule;

    private SupplierRanking supplierRanking;
    private Configuration configuration;
    private AlphaRegexCountryMapping regexMap;
    private TracerMock tracer;
    private CompositePlace compositePlace;
    private ClusteredPlace clusteredPlace;
    private ProductizedPlace productizedPlace;
    private ArchivePlace archivePlace;
    private POI poi;

    @Before
    public void setUp() {
        supplierRanking = SupplierRankMocker.mockRanking();
        alternateNamesRule = new AlternateNamesRule(supplierRanking);

        configuration = mock(Configuration.class);
        when(configuration.getLocalityByCode(BEL)).thenReturn(mock(Locality.class));
        RulesConfiguration rulesConfig = mock(RulesConfiguration.class);
        when(rulesConfig.doAlternateNameExactComparison("CHT")).thenReturn(true);
        when(rulesConfig.getAlternateNameSimilarityComparisonPercentage()).thenReturn(80.0);
        when(configuration.getRulesConfiguration()).thenReturn(rulesConfig);

        mockAlphaRegexCountryMapping();

        tracer = new TracerMock();

        initClusteredPlace();
        initProductizedPlace();
    }

    private void initClusteredPlace() {
        clusteredPlace = ClusteredPlace.newBuilder().setClusteredPlaceId(new AVUUID(2L, 1L))
            .setMatchingPlaces(new ArrayList<CompositePlace>()).build();

        compositePlace = buildDefaultCompositePlace();
        clusteredPlace.getMatchingPlaces().add(compositePlace);
    }

    private void mockAlphaRegexCountryMapping() {
        regexMap = mock(AlphaRegexCountryMapping.class);
        when(regexMap.matchAlphabetRegex(anyString(), anyString())).thenReturn(true);
        when(regexMap.matchCountryRegex(anyString(), anyString())).thenReturn(true);
        // to simulate invalid characters
        when(regexMap.matchAlphabetRegex(contains("$"), anyString())).thenReturn(false);
        when(regexMap.matchCountryRegex(contains("$"), anyString())).thenReturn(false);
        when(configuration.getAlphaRegexCountryMap()).thenReturn(regexMap);
    }

    private void initProductizedPlace() {
        productizedPlace = ProductizedPlace.newBuilder()
            .setProduct(ArchivePlace.newBuilder().setArchivePlaceId(UUIDUtil.newAVUUID(UUID.randomUUID())).build())
            .build();
        archivePlace = productizedPlace.getProduct();
        archivePlace.setIso3Country(BEL);
        poi = GP3Utils.makePOIRecord();
        archivePlace.setPois(Arrays.asList(poi));
    }

    @Test
    public final void whenUseStageIsSourceTakeNamesFromMappedPlace() {
        mockUseStageSource(compositePlace);
        testProductizeFromMappedPlace();
    }

    @Test
    public final void testApplyMandatoryFlag() {
        setProcessingFlags(ProcessingFlag.Mandatory);
        testProductizeFromMappedPlace();
    }

    @Test
    public final void testApplyTifCoreFlag() {
        setProcessingFlags(ProcessingFlag.TifCore);
        testProductizeFromMappedPlace();
    }

    @Test
    public void testSkipSimilarAlternateNamesForDifferentSuppliers() {
        setOfficialNamesInPOI(archiveName("official name 1", "ENG"), archiveName("official name 2", "ENG"));

        // preparing composite place to pick mapped place to productize names
        CompositePlace compositePlace1 = buildCompositePlaceWithNamesInMappedPlace(alternateName("Official Name 1", "en", "Latn"),
            alternateName("Name Alternate 1", "en", "Latn"),
            alternateName("Name Alternate 2", "en", null));
        compositePlace1.getNormalizedPlace().setSupplier(mockSupplier("DNB"));
        mockUseStageSource(compositePlace1);

        // preparing composite place to pick normalized place to productize names
        CompositePlace compositePlace2 = buildCompositePlaceWithNamesInNormalizedPlace(officialName("official name 1", "en", "Latn"),
            alternateName("alternate name 2", "en", "Latn"),
            alternateName("alternative name 3", "en", "Latn"),
            alternateName("official name", "en", "Latn"));
        compositePlace2.getNormalizedPlace().setSupplier(mockSupplier("TIF"));

        clusteredPlace.setMatchingPlaces(Arrays.asList(compositePlace1, compositePlace2));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace1.getMappedPlace(), 0, archiveName("Name Alternate 1", "ENG")),
            productizedName(compositePlace2.getNormalizedPlace(), 1, archiveName("alternate name 2", "ENG")));
        verifyTraceSkippedSimilarAlternateNames(compositePlace1.getMappedPlace(), 0,
            nameMatch(archiveName("official name 1", "ENG"), officialName("Official Name 1", "en", "Latn"), 100.0),
            nameMatch(archiveName("Name Alternate 1", "ENG"), alternateName("Name Alternate 2", "en", null), 93.75));
        verifyTraceSkippedSimilarAlternateNames(compositePlace2.getNormalizedPlace(), 2,
            nameMatch(archiveName("official name 1", "ENG"), officialName("official name 1", "en", "Latn"), 100.0),
            nameMatch(archiveName("alternate name 2", "ENG"), alternateName("alternative name 3", "en", "Latn"), 83.33),
            nameMatch(archiveName("official name 1", "ENG"), officialName("official name", "en", "Latn"), 86.67));
    }

    private Supplier mockSupplier(String supplierAbbrevation) {
        Supplier suppDNB = mock(Supplier.class);
        suppDNB.setId(supplierAbbrevation);
        suppDNB.setName(supplierAbbrevation);
        return suppDNB;
    }

    private void testProductizeFromMappedPlace() {
        addNamesToNormalizedPlace(alternateName("Alternate Name 2", "en", "Latn"),
            alternateName("Alternate Name 1", "en", "Latn"));

        addNamesToMappedPlace(alternateName("Alternate Name 3", "en", "Latn"),
            alternateName("Name Alternate 4", "en", "Latn"),
            alternateName("Name Alternate 5", "en", "Latn"),
            alternateName("Name Alternative", "en", "Latn"),
            alternateName("Alternate Name 7", "nl", "Latn"));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace.getMappedPlace(), 0,
            archiveName("Alternate Name 3", "ENG"),
            archiveName("Name Alternate 4", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace.getMappedPlace(),
            nameMatch(archiveName("Name Alternate 4", "ENG"), alternateName("Name Alternate 5", "en", "Latn"), 93.75),
            nameMatch(archiveName("Name Alternate 4", "ENG"), alternateName("Name Alternative", "en", "Latn"), 81.25));
    }

    @Test
    public final void whenNoNamesInPlaceNoAlternateNamesAreProductized() {
        compositePlace.getMappedPlace().setNames(null);
        compositePlace.getNormalizedPlace().setNames(null);

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyNoAlternateNamesAreProductized();
        verifyNoSkippedAlternateNameTraces();
    }

    @Test
    public final void testNamesAreSortedProperly() {
        addNamesToNormalizedPlace(alternateName("Name Alternate 2", "en", "Latn"),
            officialName("Official Name 1", "en", "Latn"), // skipped because it is similar to existing Official Name in POI
            alternateName("Alternate Name 1", "en", "Latn"));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace.getNormalizedPlace(), 0,
            archiveName("Alternate Name 1", "ENG"),
            archiveName("Name Alternate 2", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("Official Name", "ENG"), officialName("Official Name 1", "en", "Latn"), 86.67));
    }

    @Test
    public final void testApplyNoDuplicates() {
        addNamesToNormalizedPlace(alternateName("Alternate Name 1", "en", "Latn"),
            alternateName("Name Alternate 2", "en", "Latn"),
            alternateName("Alternate Name 2", "en", "Latn"), // similar to "Alternate Name 1" hence skipped
            officialName("official name", "en", "Latn")); // similar to "Official Name" hence skipped

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace.getNormalizedPlace(), 0,
            archiveName("Alternate Name 1", "ENG"),
            archiveName("Name Alternate 2", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("Alternate Name 1", "ENG"), alternateName("Alternate Name 2", "en", "Latn"), 93.75),
            nameMatch(archiveName("Official Name", "ENG"), officialName("official name", "en", "Latn"), 100.0));
    }

    @Test
    public final void testLimitThreeAlternateNamesPerLanguage() {
        CompositePlace compositePlace1 = buildCompositePlaceWithNamesInNormalizedPlace(alternateName("1 Alternate Name 1", "en", "Latn"),
            alternateName("2 Name Alternate 2", "en", "Latn"));

        CompositePlace compositePlace2 = buildCompositePlaceWithNamesInNormalizedPlace(alternateName("3rd Alternate Name", "en", "Latn"),
            alternateName("4th Name Alternative", "en", "Latn"));

        clusteredPlace.setMatchingPlaces(Arrays.asList(compositePlace1, compositePlace2));

        setOfficialNamesInPOI(archiveName("Official One", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        // official names count does not change
        assertEquals(1, archivePlace.getPois().get(0).getOfficialNames().size());

        // Alternate Name(s) from 2nd CompositePlace are added after the two alternate names from 1st CompositePlace
        // "4th Name Alternative" is not added because we limit 3 alternate names per language
        verifyAlternateNames(
            productizedName(compositePlace1.getNormalizedPlace(), 0,
                archiveName("1 Alternate Name 1", "ENG"),
                archiveName("2 Name Alternate 2", "ENG")),
            productizedName(compositePlace2.getNormalizedPlace(), 2, archiveName("3rd Alternate Name", "ENG")));

        verifyNoSkippedAlternateNameTraces();
    }

    @Test
    public void testShouldRejectSimilarAlternateName() {
        CompositePlace compositePlace1 = buildCompositePlaceWithNamesInNormalizedPlace(alternateName("Alternate Name 1", "en", "Latn"),
            alternateName("Name Alternate 2", "en", "Latn"),
            alternateName("Name Alternate 3", "en", null));

        CompositePlace compositePlace2 = buildCompositePlaceWithNamesInNormalizedPlace(alternateName("alternate name 1", "en", "Latn"),
            alternateName("alternate name 2", "en", "Latn"),
            alternateName("alternative name 3", "en", "Latn"),
            alternateName("official name", "en", "Latn"));

        clusteredPlace.setMatchingPlaces(Arrays.asList(compositePlace1, compositePlace2));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        // "alternate name 1" and "alternate name 2" are not added because they are similar as "Alternate Name 1"
        // And "official name" is not added because it is similar to already present "Official Name"
        verifyAlternateNames(
            productizedName(compositePlace1.getNormalizedPlace(), 0,
                archiveName("Alternate Name 1", "ENG"),
                archiveName("Name Alternate 2", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace1.getNormalizedPlace(), 0,
            nameMatch(archiveName("Name Alternate 2", "ENG"), alternateName("Name Alternate 3", "en", null), 93.75));

        verifyTraceSkippedSimilarAlternateNames(compositePlace2.getNormalizedPlace(), 1,
            nameMatch(archiveName("Alternate Name 1", "ENG"), alternateName("alternate name 1", "en", "Latn"), 100.0),
            nameMatch(archiveName("Alternate Name 1", "ENG"), alternateName("alternate name 2", "en", "Latn"), 93.75),
            nameMatch(archiveName("Alternate Name 1", "ENG"), alternateName("alternative name 3", "en", "Latn"), 83.33),
            nameMatch(archiveName("Official Name", "ENG"), officialName("official name", "en", "Latn"), 100.0));
    }

    @Test
    public void testShouldRejectAlternateNameWithInvalidCharacter() {
        int LOCID = 82600;
        mockAlphaRegexCountryMap(LOCID);
        mockLocality("gb", "gbr", LOCID, 2, 44, "826", "");
        archivePlace.setIso3Country("gbr");
        setProcessingFlags(ProcessingFlag.Mandatory);

        addNamesToNormalizedPlace(alternateName("WinhÃ¶ring 2", "en", "Latn"),
            officialName("Official Name 1", "en", "Latn"),
            alternateName("WinhÃ¶ring", "en", "Latn"),
            officialName("Official Name 2", "en", "Latn"));

        addNamesToMappedPlace(officialName("Official 3", "en", "Latn"),
            officialName("Official 4", "en", "Latn"),
            alternateName("WinhÃ¶ring 1", "en", "Latn"),
            alternateName("Normal 1", "en", "Latn"),
            alternateName("Normal Name 2", "en", "Latn"),
            alternateName("RinhÃ¶ring 2", "en", "Latn"),
            alternateName("WinhÃ¶ring 3", "nl", null));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        // Mandatory ProcessingFlag is set, so names from Mapped Place are taken
        verifyAlternateNames(productizedName(compositePlace.getMappedPlace(), 0,
            archiveName("Normal 1", "ENG"),
            archiveName("Normal Name 2", "ENG"),
            archiveName("Official 3", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace.getMappedPlace(),
            nameMatch(archiveName("Official 3", "ENG"), alternateName("Official 4", "en", "Latn"), 90.0));

        verifyTraceInvalidNames(compositePlace.getMappedPlace(), TraceType.NamesRule_InvalidCharactersInName,
            alternateName("WinhÃ¶ring 1", "en", "Latn"),
            alternateName("RinhÃ¶ring 2", "en", "Latn"),
            alternateName("WinhÃ¶ring 3", "nl", null));
    }

    @Test
    public void testShouldRejectAlternateNameWithInvalidZeroInAN() {
        int LOCID = 82600;
        mockAlphaRegexCountryMap(LOCID);
        mockLocality("gb", "gbr", LOCID, 2, 44, "826", "");
        archivePlace.setIso3Country("gbr");
        setProcessingFlags(ProcessingFlag.Mandatory);

        addNamesToNormalizedPlace(alternateName("AlternateNPL1 name0", "en", "Latn"),
            alternateName("0AlternateNPL2 name", "en", "Latn"),
            alternateName("Alter00nateNPL3 name", "en", "Latn"),
            alternateName("AlternateNPL name", "en", "Latn"));

        addNamesToMappedPlace(
            alternateName("AlternateMPL1 name0", "en", "Latn"),
            officialName("AlternateMPL1 name", "en", "Latn"),
            alternateName("Alterna00teMPL1 name", "en", "Latn"),
            alternateName("AlternateMPL1 name", "en", "Latn"));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace.getMappedPlace(), 0,
            archiveName("AlternateMPL1 name", "ENG")));

        verifyTraceInvalidNames(compositePlace.getMappedPlace(), TraceType.NamesRule_InvalidZeroInName,
            alternateName("AlternateMPL1 name0", "en", "Latn"),
            alternateName("Alterna00teMPL1 name", "en", "Latn"));
    }

    @Test
    public void whenAllNamesHavingInvalidCharactersNoAlternateNameIsProductized() {
        int LOCID = 82600;
        mockAlphaRegexCountryMap(LOCID);
        mockLocality("gb", "gbr", LOCID, 2, 44, "826", "");
        archivePlace.setIso3Country("gbr");
        setProcessingFlags(ProcessingFlag.Mandatory);

        addNamesToNormalizedPlace(alternateName("WinhÃ¶ring 2", "en", "Latn"),
            officialName("Official Name 1", "en", "Latn"),
            alternateName("WinhÃ¶ring", "en", "Latn"),
            officialName("Official Name 2", "en", "Latn"));

        addNamesToMappedPlace(officialName("Official 3Ã¶", "en", "Latn"),
            alternateName("WinhÃ¶ring 1", "en", "Latn"),
            alternateName("RinhÃ¶ring 2", "en", "Latn"),
            alternateName("WinhÃ¶ring 3", "nl", null));

        setOfficialNamesInPOI(archiveName("Official Name", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        // Mandatory ProcessingFlag is set, so names from Mapped Place are taken
        verifyNoAlternateNamesAreProductized();
        verifyNoSkippedAlternateNameTraces();

        verifyTraceInvalidNames(compositePlace.getMappedPlace(), TraceType.NamesRule_InvalidCharactersInName,
            officialName("Official 3Ã¶", "en", "Latn"),
            alternateName("WinhÃ¶ring 1", "en", "Latn"),
            alternateName("RinhÃ¶ring 2", "en", "Latn"), alternateName("WinhÃ¶ring 3", "nl", null));
    }

    private void mockLocality(String iso2Name, String iso3Name, int LOCID, int type, int tif, String isocountry2, String isostate2) {
        Locality locality = new Locality(LOCID, type, tif, isocountry2, isostate2);
        LocalityName localityName = mock(LocalityName.class);
        locality.setName(localityName);
        locality.setISO2Name(iso2Name);
        locality.setISO3Name(iso3Name);
        when(configuration.getLocalityByCode(iso3Name)).thenReturn(locality);
    }

    private void mockAlphaRegexCountryMap(int LOCID) {
        AlphaRegexCountryMapping mapping = CharacterTestHelper.createMapping(String.valueOf(LOCID));
        when(configuration.getAlphaRegexCountryMap()).thenReturn(mapping);
    }

    @Test
    public void testManyAlternateNames() {
        addNamesToNormalizedPlace(officialName("Royal Palace", "en", null),// skipped because same official name already present
            alternateName("Koninklijk Paleis", "nl", null),// skipped because same official name already present
            alternateName("Het Paleis", "nl", null),// added in POI
            alternateName("Het Paleis Van De Koning", "nl", null),// added in POI
            alternateName("Palais Royal", "fr", null),// skipped because same official name already present
            alternateName("Palace", "en", null), // added in POI
            alternateName("Palace$", "en", "Latn"), // skipped because has Invalid character
            alternateName("The Palace", "en", null), // skipped because already 3 other ENG names get added
            alternateName("The King's Palace", "en", null), // added in POI
            alternateName("The King and Queen's Palace", "en", null), // added in POI
            alternateName("Palacio Real", "es", "Latn"), // skipped because same official name already present
            alternateName("Königliche Palast", "de", null)); // skipped because official name with "de" language is not present

        setOfficialNamesInPOI(archiveName("Koninklijk Paleis", "DUT"),
            archiveName("Royal Palace", "ENG"),
            archiveName("Palais Royal", "FRE"),
            archiveName("Palacio Real", "SPA"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        // alternate names with invalid characters are not added
        // only alternate names in languages that occur in the official names are added
        // a maximum of three alternate names per language is added
        // alternate names are added in a deterministic order (sorted first on language then on name alphabetically)
        verifyAlternateNames(productizedName(compositePlace.getNormalizedPlace(), 0,
            archiveName("Het Paleis", "DUT"),
            archiveName("Het Paleis Van De Koning", "DUT"),
            archiveName("Palace", "ENG"),
            archiveName("The King and Queen's Palace", "ENG"),
            archiveName("The King's Palace", "ENG")));

        verifyTraceSkippedSimilarAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("Royal Palace", "ENG"), officialName("Royal Palace", "en", null), 100.0),
            nameMatch(archiveName("Koninklijk Paleis", "DUT"), officialName("Koninklijk Paleis", "nl", null), 100.0),
            nameMatch(archiveName("Palais Royal", "FRE"), officialName("Palais Royal", "fr", null), 100.0),
            nameMatch(archiveName("Palacio Real", "SPA"), officialName("Palacio Real", "es", "Latn"), 100.0));

        verifyTraceInvalidNames(compositePlace.getNormalizedPlace(), TraceType.NamesRule_InvalidCharactersInName,
            alternateName("Palace$", "en", "Latn"));
    }

    @Test
    public void testDoAlternateNameExactComparison() {
        addNamesToNormalizedPlace(officialName("皇宮", "zh", "Hant"));

        setOfficialNamesInPOI(archiveName("皇宮", "CHT"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyNoAlternateNamesAreProductized();
        verifyTraceSkippedEqualAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("皇宮", "CHT"), officialName("皇宮", "zh", "Hant")));
    }

    @Test
    public void allNamesInMultipleLanguagesWithDifferentComparisonAreSkipped() {
        addNamesToNormalizedPlace(officialName("皇宮", "zh", "Hant"),
            officialName("Royal Palace", "en", "Latn"));

        setOfficialNamesInPOI(archiveName("皇宮", "CHT"),
            archiveName("Royal Palace", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyNoAlternateNamesAreProductized();
        verifyTraceSkippedSimilarAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("Royal Palace", "ENG"), officialName("Royal Palace", "en", "Latn"), 100.0));
        verifyTraceSkippedEqualAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("皇宮", "CHT"), officialName("皇宮", "zh", "Hant")));
    }

    @Test
    public void namesInMultipleLanguagesWithDifferentComparison() {
        addNamesToNormalizedPlace(officialName("皇宮", "zh", "Hant"),
            officialName("Palace Royal", "en", "Latn"));

        setOfficialNamesInPOI(archiveName("皇宮", "CHT"),
            archiveName("Royal Palace", "ENG"));

        alternateNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyAlternateNames(productizedName(compositePlace.getNormalizedPlace(), 0,
            archiveName("Palace Royal", "ENG")));

        verifyTraceSkippedEqualAlternateNames(compositePlace.getNormalizedPlace(),
            nameMatch(archiveName("皇宮", "CHT"), officialName("皇宮", "zh", "Hant")));
    }

    @Test
    public final void testGetDescription() {
        assertNotNull(alternateNamesRule.getDescription());
        assertFalse(alternateNamesRule.getDescription().isEmpty());
    }

    private void mockUseStageSource(CompositePlace compositePlace) {
        NormalizedPlace normalizedPlace = compositePlace.getNormalizedPlace();
        SupplierRank rank = mock(SupplierRank.class);
        when(rank.getNamesUseStage()).thenReturn(UseStage.SOURCE);

        when(supplierRanking.getRank(normalizedPlace.getSupplier(), normalizedPlace.getPreemptiveCategories(),
            NCategories.getAllCategories(normalizedPlace),
            normalizedPlace.getBrands(), normalizedPlace.getLocality(), normalizedPlace.getProcessingFlags())).thenReturn(rank);
    }

    private void setProcessingFlags(ProcessingFlag... processingFlag) {
        compositePlace.getNormalizedPlace().setProcessingFlags(Arrays.asList(processingFlag));
    }

    private void addNamesToMappedPlace(NName... names) {
        addNames(compositePlace.getMappedPlace(), names);
    }

    private void addNamesToNormalizedPlace(NName... names) {
        addNames(compositePlace.getNormalizedPlace(), names);
    }

    private void addNames(NormalizedPlace place, NName... names) {
        place.getNames().addAll(Arrays.asList(names));
    }

    /**
     * There is only one Tracer that is used to trace Alternate Names added to POI from different CompositePlaces.<br>
     * Let's say if a ClusteredPlace contains two CompositePlaces and from 1st CompositPlace two alternate names were added to POI.<br>
     * And from 2nd CompositePlace, one alternate name is added to POI then in tracer attributes List will have three items.<br>
     * In the POI also the alternateNames list will have three items. The index of alternate name in POI and index of trace attributes in
     * Tracer will be same.
     */
    private void verifyAlternateNames(ProductizedAlternateName... expectedANs) {
        List<ArchiveName> alternateNamesInPOI = archivePlace.getPois().get(0).getAlternateNames();
        assertEquals(totalAlternateNames(expectedANs), alternateNamesInPOI.size());

        int traceIndex = 0;
        for (ProductizedAlternateName expectedAN : expectedANs) {
            verifyGeneratedAlternateNameTrace(expectedAN.fromPlace, 1);
            verifyAlternateNames(expectedAN, traceIndex++, alternateNamesInPOI);
        }
    }

    private void verifyAlternateNames(ProductizedAlternateName expectedAN, int traceIndex, List<ArchiveName> alternateNamesInPOI) {
        int alternateNameCounter = 1;
        for (ArchiveName alternateName : expectedAN.alternateNames) {
            int currAlternateNameIndex = expectedAN.alternateNameStartIndexInPOI + alternateNameCounter - 1; // counter is 1 based
            verifyArchiveNameAndLanguage(alternateName, alternateNamesInPOI.get(currAlternateNameIndex));
            verifyTraceAttributes(alternateName, traceIndex, alternateNameCounter, TraceType.AlternateNamesRule_GeneratedAlternateName);
            alternateNameCounter++;
        }
    }

    private int totalAlternateNames(ProductizedAlternateName... expectedArchiveNames) {
        int count = 0;
        for (ProductizedAlternateName productizedName : expectedArchiveNames) {
            count += productizedName.alternateNames.size();
        }
        return count;
    }

    private void verifyTraceInvalidNames(NormalizedPlace place, TraceType traceType, NName... invalidANs) {
        verifyTrace(place, invalidANs.length, traceType);

        int traceAttrIndex = -1;
        for (NName invalidAN : invalidANs) {
            traceAttrIndex++;
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("name type", invalidAN.getType());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("skipped name", invalidAN.getText().getValue());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("language", invalidAN.getText().getLanguage());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("script", invalidAN.getText().getScript());
        }
    }

    private void verifyGeneratedAlternateNameTrace(NormalizedPlace place, int expectedInvocations) {
        verifyTrace(place, expectedInvocations, TraceType.AlternateNamesRule_GeneratedAlternateName);
    }

    private void verifyTraceSkippedSimilarAlternateNames(NormalizedPlace place, NameMatchResult... nameMatchResults) {
        verifyTraceMatchingAlternateNamesSkipped(place, 0, TraceType.AlternateNamesRule_SkippedSimilarAlternateName, nameMatchResults);
    }

    private void verifyTraceSkippedSimilarAlternateNames(NormalizedPlace place, int traceAttrStartIndex,
        NameMatchResult... nameMatchResults) {
        verifyTraceMatchingAlternateNamesSkipped(place, traceAttrStartIndex, TraceType.AlternateNamesRule_SkippedSimilarAlternateName,
            nameMatchResults);
    }

    private void verifyTraceSkippedEqualAlternateNames(NormalizedPlace place, NameMatchResult... nameMatchResults) {
        verifyTraceMatchingAlternateNamesSkipped(place, 0, TraceType.AlternateNamesRule_SkippedEqualAlternateName, nameMatchResults);
    }

    private void verifyTraceMatchingAlternateNamesSkipped(NormalizedPlace place, int traceAttrStartIndex, TraceType traceType,
        NameMatchResult... nameMatchResults) {
        verifyTrace(place, nameMatchResults.length, traceType);

        int traceAttrIndex = traceAttrStartIndex;
        for (NameMatchResult match : nameMatchResults) {
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("skipped alternate name", match.getSkippedName());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("language", match.getSkippedNameIso2Lang());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("script", match.getSkippedNameScript());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("similar name", match.getMatchedName().getName());
            verify(tracer.getAttributes(traceAttrIndex, traceType)).param("similar name type", match.getMatchedNameType());

            if (match.isSimilarityMatch()) {
                verify(tracer.getAttributes(traceAttrIndex, traceType)).param("similarity score", match.getSimilarityScore().get());
            }
            traceAttrIndex++;
        }
    }

    private NameMatchResult nameMatch(ArchiveName matchedName, NName skippedName) {
        return nameMatch(matchedName, skippedName, null);
    }

    private NameMatchResult nameMatch(ArchiveName matchedName, NName skippedName, Double similarityScore) {
        return new NameMatchResult(matchedName, skippedName.getType(), alternateName(skippedName), Optional.fromNullable(similarityScore));
    }

    private void verifyTrace(NormalizedPlace place, int expectedInvocations, TraceType traceType) {
        verify(tracer.getMock(), Mockito.times(expectedInvocations)).info(place.getDeliveryPlaceId().toString(), Phase.Productizing,
            traceType);
    }

    private void verifyTraceAttributes(ArchiveName expectedArchiveName, int traceAttrIndex, int paramCounter, TraceType traceType) {
        Language isoLang = RECODER.tifToIsoLanguage(expectedArchiveName.getLanguage().toString());

        verify(tracer.getAttributes(traceAttrIndex, traceType)).param("alternate name " + paramCounter, expectedArchiveName.getName());
        verify(tracer.getAttributes(traceAttrIndex, traceType)).param("language " + paramCounter, isoLang.getLanguage());
        verify(tracer.getAttributes(traceAttrIndex, traceType)).param("script " + paramCounter, isoLang.getScript());
    }

    private void verifyNoAlternateNamesAreProductized() {
        verifyGeneratedAlternateNameTrace(compositePlace.getNormalizedPlace(), 0);
        assertEquals(0, poi.getAlternateNames().size());
    }

    private void verifyNoSkippedAlternateNameTraces() {
        verifyTrace(compositePlace.getNormalizedPlace(), 0, TraceType.AlternateNamesRule_SkippedEqualAlternateName);
        verifyTrace(compositePlace.getNormalizedPlace(), 0, TraceType.AlternateNamesRule_SkippedSimilarAlternateName);
        verifyTrace(compositePlace.getNormalizedPlace(), 0, TraceType.NamesRule_InvalidCharactersInName);
    }

    private ProductizedAlternateName productizedName(NormalizedPlace fromPlace, int alternateNameStartIndexInPOI,
        ArchiveName... alternateNames) {
        return new ProductizedAlternateName(fromPlace, alternateNameStartIndexInPOI, alternateNames);
    }

    private ArchiveName archiveName(String name, String tifLanguage) {
        return new ArchiveName(name, tifLanguage);
    }

    private void setOfficialNamesInPOI(ArchiveName... archiveNames) {
        poi.setOfficialNames(Arrays.asList(archiveNames));
    }

    private void verifyArchiveNameAndLanguage(ArchiveName expectedArchiveName, ArchiveName actualArchiveName) {
        verifyArchiveNameAndLanguage(expectedArchiveName.getName(), expectedArchiveName.getLanguage(), actualArchiveName);
    }

    private void verifyArchiveNameAndLanguage(CharSequence expectedName, CharSequence expectedLanguage, ArchiveName actualArchiveName) {
        assertEquals(expectedName, actualArchiveName.getName().toString());
        assertEquals(expectedLanguage, actualArchiveName.getLanguage().toString());
    }

    private CompositePlace buildDefaultCompositePlace() {
        NormalizedPlace mappedPlace = buildDefaultNormalizedPlace();
        NormalizedPlace normalizedPlace = buildDefaultNormalizedPlace();
        return CompositePlace.newBuilder().setMappedPlace(mappedPlace).setNormalizedPlace(normalizedPlace).build();
    }

    private CompositePlace buildCompositePlaceWithNamesInNormalizedPlace(NName... names) {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        addNames(compositePlace.getNormalizedPlace(), names);
        return compositePlace;
    }

    private CompositePlace buildCompositePlaceWithNamesInMappedPlace(NName... names) {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        addNames(compositePlace.getMappedPlace(), names);
        return compositePlace;
    }

    private NormalizedPlace buildDefaultNormalizedPlace() {
        return NormalizedPlace.newBuilder()
            .setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID()))
            .setNames(new ArrayList<NName>())
            .build();
    }

    private NName officialName(String name, String language, String script) {
        return name(NameType.Official, name, language, script);
    }

    private NName alternateName(String name, String language, String script) {
        return name(NameType.Alternative, name, language, script);
    }

    private AlternateName alternateName(NName name) {
        ArchiveName archiveName = archiveName(name);
        return new AlternateName(name, archiveName);
    }

    private ArchiveName archiveName(NName name) {
        return new ArchiveName(name.getText().getValue(), LanguageRecoding.getInstance().formatLanguage(name.getText()));
    }

    private NName name(NameType type, String name, String language, String script) {
        return NName.newBuilder()
            .setType(type)
            .setText(NText.newBuilder()
                .setValue(name)
                .setLanguage(language)
                .setScript(script)
                .build())
            .build();
    }

    /**
     * Put all information necessary to verify the productized alternate names.
     */
    private static class ProductizedAlternateName {

        /**
         * From which place the Alternate Names were taken.
         */
        private final NormalizedPlace fromPlace;

        /**
         * Alternate Names added in POI fromPlace
         */
        private final List<ArchiveName> alternateNames;

        /**
         * The start index in POI at which the 1st Alternate Name is present that was taken fromPlace
         */
        private final int alternateNameStartIndexInPOI;

        private ProductizedAlternateName(NormalizedPlace fromPlace, int alternateNameStartIndexInPOI, ArchiveName... alternateNames) {
            this.fromPlace = fromPlace;
            this.alternateNameStartIndexInPOI = alternateNameStartIndexInPOI;
            this.alternateNames = ImmutableList.copyOf(alternateNames);
        }
    }
}
