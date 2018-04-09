package com.tomtom.cleancode.exercise.productization;

import static com.tomtom.places.unicorn.productization.gp3.ruleimpl.VehicleTypesRule.PETROL_STATION_GDF;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveCodeValue;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.productized.ProductizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.AVUUID;
import com.tomtom.places.unicorn.domain.avro.source.VehicleType;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.productization.gp3.GP3Utils;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class VehicleTypesRuleTest {

    private VehicleTypesRule vehicletypesRule;
    private Configuration configuration;
    private TracerMock tracer;
    private ClusteredPlace clusteredPlace;
    private ProductizedPlace productizedPlace;
    private ArchivePlace archivePlace;

    @Before
    public void setUp() {
        SupplierRanking supplierRanking = SupplierRankMocker.mockRanking();

        vehicletypesRule = new VehicleTypesRule(supplierRanking);
        configuration = mock(Configuration.class);
        tracer = new TracerMock();

        clusteredPlace =
            ClusteredPlace.newBuilder().setClusteredPlaceId(AVUUID.newBuilder().setLeastSigBits(1L).setMostSigBits(2L).build())
                .setMatchingPlaces(new ArrayList<CompositePlace>()).build();

        productizedPlace =
            ProductizedPlace.newBuilder()
                .setProduct(ArchivePlace.newBuilder().setArchivePlaceId(UUIDUtil.newAVUUID(UUID.randomUUID())).build())
                .build();
        // Initialize productPlace as a GP3Place
        archivePlace = productizedPlace.getProduct();
        POI poi = GP3Utils.makePOIRecord();
        poi.setGdfFeatureCode(PETROL_STATION_GDF);
        poi.setGdfFeatureCodeDesc("Petrol Station");
        archivePlace.setPois(Arrays.asList(poi));
    }

    @Test
    public final void noVehicleTypePopulatedForNonPetrolStation() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        archivePlace.getPois().get(0).setGdfFeatureCode("7312");
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes);
    }

    @Test
    public final void populateDefaultVehicleTypesWhenNoneProvided() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes, VehicleType.PassengerCar, VehicleType.MediumTruck, VehicleType.HeavyTruck);
    }

    @Test
    public final void whenPassengerCarIsProvidedThenPopulatePassengerCar() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        setVehicleType(compositePlace, VehicleType.PassengerCar);
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes, VehicleType.PassengerCar);
    }

    private void verifyVehicleTypes(List<ArchiveCodeValue> attributes, VehicleType... vehicleTypes) {
        assertEquals(vehicleTypes.length, attributes.size());

        for (int i = 0; i < vehicleTypes.length; i++) {
            assertEquals(VehicleTypesRule.VEHICLE_TYPE + ":" + VehicleTypesRule.VEHICLE_TYPES.get(vehicleTypes[i]),
                attributes.get(i).getCode() + ":" + attributes.get(i).getValue());
        }

        verifyZeroInteractions(tracer.getMock());
    }

    @Test
    public final void whenMediumTruckIsProvidedThenPopulateMediumAndHeavyTruck() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        setVehicleType(compositePlace, VehicleType.MediumTruck);
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes, VehicleType.MediumTruck, VehicleType.HeavyTruck);
    }

    @Test
    public final void whenPassengerCarIsProvidedInHighestRankedPlaceThenPopulatePassengerCar() {
        CompositePlace compositePlaceA = buildDefaultCompositePlace();
        CompositePlace compositePlaceB = buildDefaultCompositePlace();

        setVehicleType(compositePlaceA, VehicleType.PassengerCar);
        setVehicleType(compositePlaceB, VehicleType.MediumTruck);

        clusteredPlace.getMatchingPlaces().add(compositePlaceA);
        clusteredPlace.getMatchingPlaces().add(compositePlaceB);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes, VehicleType.PassengerCar);
    }

    @Test
    public final void whenNoVehicleTypeInHighestRankedPlaceThenPopulateFromNextRankedPlace() {
        CompositePlace compositePlaceA = buildDefaultCompositePlace();
        CompositePlace compositePlaceB = buildDefaultCompositePlace();
        CompositePlace compositePlaceC = buildDefaultCompositePlace();

        setVehicleType(compositePlaceA, null);
        setVehicleType(compositePlaceB, VehicleType.PassengerCar);
        setVehicleType(compositePlaceC, VehicleType.MediumTruck);

        clusteredPlace.getMatchingPlaces().add(compositePlaceA);
        clusteredPlace.getMatchingPlaces().add(compositePlaceB);
        clusteredPlace.getMatchingPlaces().add(compositePlaceC);

        vehicletypesRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        List<ArchiveCodeValue> attributes = archivePlace.getPois().get(0).getAttributes();
        verifyVehicleTypes(attributes, VehicleType.PassengerCar);
    }

    private CompositePlace buildDefaultCompositePlace() {

        NormalizedPlace mappedPlace = buildDefaultNormalizedPlace();
        NormalizedPlace normalizedPlace = buildDefaultNormalizedPlace();

        CompositePlace compositePlace = CompositePlace.newBuilder().setMappedPlace(mappedPlace).setNormalizedPlace(normalizedPlace).build();

        return compositePlace;
    }

    private NormalizedPlace buildDefaultNormalizedPlace() {

        NormalizedPlace normalizedPlace = NormalizedPlace.newBuilder()
            .setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID()))
            .build();

        return normalizedPlace;
    }

    private void setVehicleType(CompositePlace compositePlace, VehicleType vehicleType) {
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicleType);
        compositePlace.getNormalizedPlace().setVehicleTypes(vehicleTypes);
    }
}
