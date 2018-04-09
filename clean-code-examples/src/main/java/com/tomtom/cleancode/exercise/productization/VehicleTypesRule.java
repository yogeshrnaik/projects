package com.tomtom.cleancode.exercise.productization;

import static com.tomtom.places.unicorn.domain.util.CharSequenceUtil.asNonNullString;

import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.domain.avro.archive.ArchiveCodeValue;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.source.VehicleType;
import com.tomtom.places.unicorn.productization.gp3.rule.AbstractGP3ProductizationRule;
import com.tomtom.places.unicorn.productization.gp3.ruleimpl.SupRecordBuilder;
import com.tomtom.places.unicorn.traces.Tracer;

public class VehicleTypesRule extends AbstractGP3ProductizationRule {

    public static final String VEHICLE_TYPE = "VT";
    public static final CharSequence PETROL_STATION_GDF = "7311";

    public static final Map<VehicleType, String> VEHICLE_TYPES = ImmutableMap.<VehicleType, String>builder()
        .put(VehicleType.PassengerCar, "11")
        .put(VehicleType.MediumTruck, "20")
        .put(VehicleType.HeavyTruck, "21")
        .build();

    public VehicleTypesRule(SupplierRanking supplierRanking) {
        super(supplierRanking);
    }

    @Override
    public final void apply(ArchivePlace archivePlace, ClusteredPlace clusteredPlace, Configuration configuration, Tracer tracer) {
        POI poi = getPoi(archivePlace);
        if (!PETROL_STATION_GDF.equals(poi.getGdfFeatureCode())) {
            return;
        }

        ArchivePlace oldArchivePlace = ArchivePlace.newBuilder(archivePlace).build();

        for (CompositePlace compositePlace : getCompositePlaces(clusteredPlace)) {
            assignVehicleTypeFromCompositePlace(archivePlace, tracer, poi, compositePlace);

            if (!oldArchivePlace.equals(archivePlace)) {
                // archivePlace has changed
                SupRecordBuilder.addSupRecord(archivePlace, compositePlace);
                // stop when one place has supplied the attribute
                break;
            }
        }

        populateMediumAndHeavyTruckIfAnyOnePresent(poi);

        if (oldArchivePlace.equals(archivePlace)) {
            populateDefaultVehicleTypes(poi);
        }

    }

    private void populateDefaultVehicleTypes(POI poi) {
        for (VehicleType vehicleType : VEHICLE_TYPES.keySet()) {
            populateArchiveCode(poi, VEHICLE_TYPE, VEHICLE_TYPES.get(vehicleType));
        }
    }

    private void populateMediumAndHeavyTruckIfAnyOnePresent(POI poi) {
        if (poiHasSpecifiedVehicleType(poi, VEHICLE_TYPES.get(VehicleType.MediumTruck))
            && !poiHasSpecifiedVehicleType(poi, VEHICLE_TYPES.get(VehicleType.HeavyTruck))) {
            populateArchiveCode(poi, VEHICLE_TYPE, VEHICLE_TYPES.get(VehicleType.HeavyTruck));
        }

        if (poiHasSpecifiedVehicleType(poi, VEHICLE_TYPES.get(VehicleType.HeavyTruck))
            && !poiHasSpecifiedVehicleType(poi, VEHICLE_TYPES.get(VehicleType.MediumTruck))) {
            populateArchiveCode(poi, VEHICLE_TYPE, VEHICLE_TYPES.get(VehicleType.MediumTruck));
        }
    }

    private void assignVehicleTypeFromCompositePlace(ArchivePlace archivePlace, Tracer tracer, POI poi, CompositePlace compositePlace) {
        List<VehicleType> vehicleTypes = compositePlace.getNormalizedPlace().getVehicleTypes();
        if (vehicleTypes != null) {
            for (VehicleType vehicleType : vehicleTypes) {
                if (VEHICLE_TYPES.containsKey(vehicleType)) {
                    populateArchiveCode(poi, VEHICLE_TYPE, VEHICLE_TYPES.get(vehicleType));
                }
            }
        }
    }

    private void populateArchiveCode(POI poi, CharSequence code, String value) {
        poi.getAttributes().add(ArchiveCodeValue.newBuilder().setCode(code).setValue(value).build());
    }

    private boolean poiHasSpecifiedVehicleType(POI poi, final CharSequence vehicleTypeValue) {
        return Iterables.any(poi.getAttributes(), new Predicate<ArchiveCodeValue>() {

            @Override
            public boolean apply(ArchiveCodeValue attribute) {
                return VEHICLE_TYPE.equals(attribute.getCode()) && asNonNullString(attribute.getValue()).equals(vehicleTypeValue);
            }
        });

    }

    @Override
    public String getDescription() {
        return "Maps vehicle types.";
    }

}
