package com.tomtom.cleancode.exercise.initialnormalization;

import static com.tomtom.places.unicorn.domain.avro.source.VehicleType.HeavyTruck;
import static com.tomtom.places.unicorn.domain.avro.source.VehicleType.MediumTruck;
import static com.tomtom.places.unicorn.domain.avro.source.VehicleType.PassengerCar;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.domain.avro.normalized.Locality;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.PetrolFlag;
import com.tomtom.places.unicorn.domain.avro.source.SourcePlace;
import com.tomtom.places.unicorn.domain.avro.source.VehicleType;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.initialnormalization.ruleimpl.VehicleTypesRule;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class VehicleTypesRuleTest {

    private static final long BELGIUM = 5600L;
    private Configuration configuration;
    private SourcePlace sourcePlace;
    private NormalizedPlace mappedPlace;
    private VehicleTypesRule vehicleTypesRule;
    private TracerMock mockTracer;

    @Before
    public void setUp() {
        configuration = mock(Configuration.class);
        sourcePlace = createSourcePlace();
        mappedPlace = createMappedPlace(sourcePlace);
        vehicleTypesRule = new VehicleTypesRule();
        mockTracer = new TracerMock();
    }

    @Test
    public void shouldApplyVehicleTypes() {
        sourcePlace.setVehicleTypes(Arrays.asList(HeavyTruck, MediumTruck, PassengerCar));

        mappedPlace = vehicleTypesRule.apply(sourcePlace, mappedPlace, configuration, mockTracer);

        verifyVehicleTypes(mappedPlace.getVehicleTypes(), HeavyTruck, MediumTruck, PassengerCar);
    }

    @Test
    public void shouldPopulateMediumAndHeavyTruckWhenMediumTruckProvided() {
        sourcePlace.setVehicleTypes(Arrays.asList(MediumTruck));

        mappedPlace = vehicleTypesRule.apply(sourcePlace, mappedPlace, configuration, mockTracer);

        verifyVehicleTypes(mappedPlace.getVehicleTypes(), HeavyTruck, MediumTruck);
    }

    @Test
    public void shouldNotPopulateDefaultVehicleTypesForNonPetrolStatiosWhenNoneProvided() {
        sourcePlace.setVehicleTypes(Lists.<VehicleType>newArrayList());

        mappedPlace = vehicleTypesRule.apply(sourcePlace, mappedPlace, configuration, mockTracer);

        verifyVehicleTypes(mappedPlace.getVehicleTypes());
    }

    @Test
    public void shouldPopulateDefaultVehicleTypesForPetrolStatiosWhenNoneProvided() {
        sourcePlace.setVehicleTypes(Lists.<VehicleType>newArrayList());

        mappedPlace.setPetrolFlags(Arrays.asList(PetrolFlag.Petrol));

        mappedPlace = vehicleTypesRule.apply(sourcePlace, mappedPlace, configuration, mockTracer);

        verifyVehicleTypes(mappedPlace.getVehicleTypes(), HeavyTruck, MediumTruck, PassengerCar);
    }

    @Test
    public void discardDuplicateVehicleTypes() {
        sourcePlace.setVehicleTypes(Arrays.asList(HeavyTruck, HeavyTruck, MediumTruck, PassengerCar));

        mappedPlace = vehicleTypesRule.apply(sourcePlace, mappedPlace, configuration, mockTracer);

        verifyVehicleTypes(mappedPlace.getVehicleTypes(), HeavyTruck, MediumTruck, PassengerCar);
    }

    private void verifyVehicleTypes(List<VehicleType> vehicleTypes, VehicleType... expectedVehicleTypes) {
        assertEquals(expectedVehicleTypes.length, vehicleTypes.size());

        for (int i = 0; i < expectedVehicleTypes.length; i++) {
            assertTrue(vehicleTypes.contains(expectedVehicleTypes[i]));
        }
    }

    private SourcePlace createSourcePlace() {
        return SourcePlace.newBuilder().setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID())).build();
    }

    private NormalizedPlace createMappedPlace(SourcePlace sourcePlace) {
        Locality localityBe = Locality.newBuilder().setId(BELGIUM).build();
        return NormalizedPlace.newBuilder().setDeliveryPlaceId(sourcePlace.getDeliveryPlaceId()).setLocality(localityBe).build();
    }

}
