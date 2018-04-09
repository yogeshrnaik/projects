package com.tomtom.cleancode.exercise.productization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveBrandName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveName;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveStreetCity;
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
import com.tomtom.places.unicorn.domain.avro.trace.Phase;
import com.tomtom.places.unicorn.domain.avro.trace.TraceType;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.productization.gp3.GP3Utils;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class OfficialNamesRuleTest {

    private OfficialNamesRule officialNamesRule;

    private Configuration configuration;

    private TracerMock tracer;

    private ClusteredPlace clusteredPlace;

    private ProductizedPlace productizedPlace;

    ArchivePlace archivePlace;

    @Before
    public void setUp() {
        SupplierRanking supplierRanking = SupplierRankMocker.mockRanking();

        officialNamesRule = new OfficialNamesRule(supplierRanking);
        configuration = mock(Configuration.class);
        tracer = new TracerMock();

        clusteredPlace =
            ClusteredPlace.newBuilder().setClusteredPlaceId(AVUUID.newBuilder().setLeastSigBits(1L).setMostSigBits(2L).build())
                .setMatchingPlaces(new ArrayList<CompositePlace>()).build();

        productizedPlace =
            ProductizedPlace.newBuilder().setProduct(ArchivePlace.newBuilder().setArchivePlaceId(UUIDUtil.newAVUUID(UUID.randomUUID())).build())
                .build();
        // Initialize productPlace as a GP3Place
        archivePlace = (ArchivePlace)productizedPlace.getProduct();
        archivePlace.setPois(Arrays.asList(GP3Utils.makePOIRecord()));
    }

    @Test
    public final void testApply() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 3", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 2", "ln", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 4", "fr", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 5", "de", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 6", "ro", "Latn", "MD"));
        names.add(buildOneName(NameType.Official, "Official Name 7", "ca", "Latn", "ES", "valencia"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();

        assertEquals("Official Name 1", officialNames.get(0).getName().toString());
        assertEquals("ENG", officialNames.get(0).getLanguage().toString());

        assertEquals("Official Name 4", officialNames.get(1).getName().toString());
        assertEquals("FRE", officialNames.get(1).getLanguage().toString());

        assertEquals("Official Name 5", officialNames.get(2).getName().toString());
        assertEquals("GER", officialNames.get(2).getLanguage().toString());

        assertEquals("Official Name 6", officialNames.get(3).getName().toString());
        assertEquals("MOL", officialNames.get(3).getLanguage().toString());

        assertEquals("Official Name 7", officialNames.get(4).getName().toString());
        assertEquals("VAL", officialNames.get(4).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());
        verify(tracer.getMock()).warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
            TraceType.LimitToOnePerLanguage);

        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testApplyUND() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 2", "ln", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "en", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();

        assertEquals("Official Name 2", officialNames.get(0).getName().toString());
        assertEquals("UND", officialNames.get(0).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());

        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testApplyNoDuplicateWithUNDLanguage() throws IOException {
        // An official name with undefined language is acceptable only if the name
        // doesn't match a name already in the official names list.
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Official, "Official Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 1", "", "Latn")); // Should be dropped
        names.add(buildOneName(NameType.Official, "Official Name 3", "fr", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 4", "", "Latn")); // Should be included
        names.add(buildOneName(NameType.Official, "Official Name 4", "", "Latn")); // Duplicate should be dropped

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size()); // has official names with language, so UND name is not accepted

        assertEquals("Official Name 1", officialNames.get(0).getName().toString());
        assertEquals("ENG", officialNames.get(0).getLanguage().toString());

        assertEquals("Official Name 3", officialNames.get(1).getName().toString());
        assertEquals("FRE", officialNames.get(1).getLanguage().toString());

        // assertEquals("Official Name 4", officialNames.get(2).getName().toString());
        // assertEquals("UND", officialNames.get(2).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());

        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testApplySameLanguageDifferentScript() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Official, "Official Name 1", "el", "Grek"));
        names.add(buildOneName(NameType.Official, "Official Name 2", "el", "Grek")); // Should be dropped
        names.add(buildOneName(NameType.Official, "Official Name 3", "el", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size());

        assertEquals("Official Name 1", officialNames.get(0).getName().toString());
        assertEquals("GRE", officialNames.get(0).getLanguage().toString());

        assertEquals("Official Name 3", officialNames.get(1).getName().toString());
        assertEquals("GRL", officialNames.get(1).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());
        verify(tracer.getMock()).warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
            TraceType.LimitToOnePerLanguage);
        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testApplyMandatoryFlag() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();

        compositePlace.getNormalizedPlace().setProcessingFlags(Arrays.asList(ProcessingFlag.Mandatory));

        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 3", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 2", "ln", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "en", "Latn"));

        names = compositePlace.getMappedPlace().getNames();
        names.add(buildOneName(NameType.Official, "Official Name 4", "fr", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 5", "de", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size());

        assertEquals("Official Name 4", officialNames.get(0).getName().toString());
        assertEquals("FRE", officialNames.get(0).getLanguage().toString());

        assertEquals("Official Name 5", officialNames.get(1).getName().toString());
        assertEquals("GER", officialNames.get(1).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());

        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testApplyTifCoreFlag() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();

        compositePlace.getNormalizedPlace().setProcessingFlags(Arrays.asList(ProcessingFlag.TifCore));

        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 3", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 2", "ln", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "en", "Latn"));

        names = compositePlace.getMappedPlace().getNames();
        names.add(buildOneName(NameType.Official, "Official Name 4", "fr", "Latn"));
        names.add(buildOneName(NameType.Official, "Official Name 5", "de", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size());

        assertEquals("Official Name 4", officialNames.get(0).getName().toString());
        assertEquals("FRE", officialNames.get(0).getLanguage().toString());

        assertEquals("Official Name 5", officialNames.get(1).getName().toString());
        assertEquals("GER", officialNames.get(1).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());

        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testGenerateOfficialNameFromLocality() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        // has no names, so names will bet built from locality information.

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        assertNotNull(archivePlace);
        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        // Set gdf feature code to one configured to build official name from locality
        poi.setGdfFeatureCode("7324"); // Post Office

        // The following are usually set by the AddressesRule rule, which isn't called here.
        setStreetCity(poi, "Streetname", "dut", "Cityname", "dut");

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        assertEquals("Cityname Streetname", poi.getOfficialNames().get(0).getName().toString());
        assertEquals("dut", poi.getOfficialNames().get(0).getLanguage().toString());

        verify(tracer.getMock()).warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
            TraceType.OfficialNamesRule_GeneratedOfficialNameFromLocality);
        verify(tracer.getAttributes(0)).param("generated name", "Cityname Streetname");
    }

    @Test
    public final void testGenerateOfficialNameFromBrands() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        // has no names, so names will bet built from brand and locality information.

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        assertNotNull(archivePlace);
        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        // Set gdf feature code to one configured to build official name from brands
        poi.setGdfFeatureCode("7315"); // Restaurant

        // The following are usually set by the AddressesRule rule, which isn't called here.
        setStreetCity(poi, "Streetname", "en", "Cityname", "en");

        poi.setBrandNames(new ArrayList<ArchiveBrandName>());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName1").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName2").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName3").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName4").setAlphabet("Latn").build());

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> names = poi.getOfficialNames();

        assertEquals("BrandName1 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        names = poi.getAlternateNames();

        assertEquals("BrandName2 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        assertEquals("BrandName3 Cityname Streetname", names.get(1).getName().toString());
        assertEquals("en", names.get(1).getLanguage().toString());

        assertEquals("BrandName4 Cityname Streetname", names.get(2).getName().toString());
        assertEquals("en", names.get(2).getLanguage().toString());

        verify(tracer.getMock(), times(1))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedOfficialNameFromBrand);
        verify(tracer.getMock(), times(3))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedAlternateNameFromBrand);
        verify(tracer.getAttributes(0)).param("generated name", "BrandName1 Cityname Streetname");
        verify(tracer.getAttributes(1)).param("generated name", "BrandName2 Cityname Streetname");
        verify(tracer.getAttributes(2)).param("generated name", "BrandName3 Cityname Streetname");
        verify(tracer.getAttributes(3)).param("generated name", "BrandName4 Cityname Streetname");
    }

    @Test
    public final void testGenerateNamesFromBrandsNoDuplicateAlternateNames() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        // has no names, so names will bet built from brand and locality information.
        // Make sure no duplicates are created from the brands.
        // BrandNamesRule which runs first does duplicate checking, but just in case...

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        assertNotNull(archivePlace);
        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        // Set gdf feature code to one configured to build official name from brands
        poi.setGdfFeatureCode("7315"); // Restaurant

        // The following are usually set by the AddressesRule rule, which isn't called here.
        setStreetCity(poi, "Streetname", "en", "Cityname", "en");

        poi.setBrandNames(new ArrayList<ArchiveBrandName>());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName1").setAlphabet("Latn").build());
        // Make the second brand name a duplicate of the first.
        // BrandNamesRule should keep this from happening, but just in case...
        // This brand should not be used to create an alternate name because an alternate name can't match the official name.
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName1").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName2").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName3").setAlphabet("Latn").build());

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> names = poi.getOfficialNames();

        assertEquals("BrandName1 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        names = poi.getAlternateNames();
        assertEquals(2, names.size());

        assertEquals("BrandName2 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        assertEquals("BrandName3 Cityname Streetname", names.get(1).getName().toString());
        assertEquals("en", names.get(1).getLanguage().toString());

        verify(tracer.getMock(), times(1))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedOfficialNameFromBrand);
        verify(tracer.getMock(), times(2))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedAlternateNameFromBrand);
        verify(tracer.getAttributes(0)).param("generated name", "BrandName1 Cityname Streetname");
        verify(tracer.getAttributes(1)).param("generated name", "BrandName2 Cityname Streetname");
        verify(tracer.getAttributes(2)).param("generated name", "BrandName3 Cityname Streetname");
    }

    @Test
    public final void testGenerateNamesFromBrandsNoAlternateNamesMatchOfficialName() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        // has no names, so names will bet built from brand and locality information.
        // Make sure no duplicates are created from the brands.
        // BrandNamesRule which runs first does duplicate checking, but just in case...

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        assertNotNull(archivePlace);
        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        // Set gdf feature code to one configured to build official name from brands
        poi.setGdfFeatureCode("7315"); // Restaurant

        // The following are usually set by the AddressesRule rule, which isn't called here.
        setStreetCity(poi, "Streetname", "en", "Cityname", "en");

        poi.setBrandNames(new ArrayList<ArchiveBrandName>());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName1").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName2").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName3").setAlphabet("Latn").build());
        poi.getBrandNames().add(ArchiveBrandName.newBuilder().setValue("BrandName3").setAlphabet("Latn").build());

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> names = poi.getOfficialNames();

        assertEquals("BrandName1 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        names = poi.getAlternateNames();
        assertEquals(2, names.size());

        assertEquals("BrandName2 Cityname Streetname", names.get(0).getName().toString());
        assertEquals("en", names.get(0).getLanguage().toString());

        assertEquals("BrandName3 Cityname Streetname", names.get(1).getName().toString());
        assertEquals("en", names.get(1).getLanguage().toString());

        verify(tracer.getMock(), times(1))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedOfficialNameFromBrand);
        verify(tracer.getMock(), times(2))
            .warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
                TraceType.OfficialNamesRule_GeneratedAlternateNameFromBrand);
        verify(tracer.getAttributes(0)).param("generated name", "BrandName1 Cityname Streetname");
        verify(tracer.getAttributes(1)).param("generated name", "BrandName2 Cityname Streetname");
        verify(tracer.getAttributes(2)).param("generated name", "BrandName3 Cityname Streetname");
    }

    @Test
    public final void testAlternateNameFallback() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "fr", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size());

        assertEquals("Alternate Name 1", officialNames.get(0).getName().toString());
        assertEquals("ENG", officialNames.get(0).getLanguage().toString());

        assertEquals("Alternate Name 3", officialNames.get(1).getName().toString());
        assertEquals("FRE", officialNames.get(1).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());
        verify(tracer.getMock()).warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
            TraceType.LimitToOnePerLanguage);
        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void testAlternateNameFallbackEmptyOfficialName() throws IOException {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        List<NName> names = compositePlace.getNormalizedPlace().getNames();
        names.add(buildOneName(NameType.Alternative, "Alternate Name 2", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 1", "en", "Latn"));
        names.add(buildOneName(NameType.Alternative, "Alternate Name 3", "fr", "Latn"));
        names.add(buildOneName(NameType.Official, "", "en", "Latn"));

        clusteredPlace.getMatchingPlaces().add(compositePlace);

        officialNamesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        assertNotNull(archivePlace.getPois());
        POI poi = archivePlace.getPois().get(0); // First record must be poi. Others are entrypoints
        assertNotNull(poi);

        List<ArchiveName> officialNames = poi.getOfficialNames();
        assertEquals(2, officialNames.size());

        assertEquals("Alternate Name 1", officialNames.get(0).getName().toString());
        assertEquals("ENG", officialNames.get(0).getLanguage().toString());

        assertEquals("Alternate Name 3", officialNames.get(1).getName().toString());
        assertEquals("FRE", officialNames.get(1).getLanguage().toString());

        assertTrue(poi.getAlternateNames().isEmpty());
        verify(tracer.getMock()).warn(compositePlace.getNormalizedPlace().getDeliveryPlaceId(), Phase.Productizing,
            TraceType.LimitToOnePerLanguage);
        // check traces
        verifyZeroInteractions(tracer.getMock());
    }

    private CompositePlace buildDefaultCompositePlace() throws IOException {

        NormalizedPlace mappedPlace = buildDefaultNormalizedPlace();
        NormalizedPlace normalizedPlace = buildDefaultNormalizedPlace();

        CompositePlace compositePlace = CompositePlace.newBuilder().setMappedPlace(mappedPlace).setNormalizedPlace(normalizedPlace).build();

        return compositePlace;
    }

    private NormalizedPlace buildDefaultNormalizedPlace() {

        NormalizedPlace normalizedPlace = NormalizedPlace.newBuilder()
            .setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID()))
            .setNames(new ArrayList<NName>())
            .build();

        return normalizedPlace;
    }

    private NName buildOneName(NameType type, String name, String language, String script) {
        return buildOneName(type, name, language, script, null, null);
    }

    private NName buildOneName(NameType type, String name, String language, String script, String territory) {
        return buildOneName(type, name, language, script, territory, null);
    }

    private NName buildOneName(NameType type, String name, String language, String script, String territory, String variant) {
        return NName.newBuilder()
            .setType(type)
            .setText(NText.newBuilder()
                .setValue(name)
                .setLanguage(language)
                .setScript(script)
                .setTerritory(territory)
                .setVariant(variant)
                .build())
            .build();
    }

    private void setStreetCity(POI poi, String street, String streetLang, String city, String cityLang) {
        if (poi.getStreetsAndCities() == null) {
            poi.setStreetsAndCities(new ArrayList<ArchiveStreetCity>());
        }
        poi.getStreetsAndCities().add(ArchiveStreetCity.newBuilder()
            .setStreet(street)
            .setStreetLanguage(streetLang)
            .setCity(city)
            .setCityLanguage(cityLang)
            .build());
    }

    @Test
    public final void testGetDescription() {
        assertNotNull(officialNamesRule.getDescription());
        assertFalse(officialNamesRule.getDescription().isEmpty());
    }

}
