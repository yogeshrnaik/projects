package com.tomtom.cleancode.exercise.initialnormalization;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.SourcePlace;
import com.tomtom.places.unicorn.domain.avro.source.VehicleType;
import com.tomtom.places.unicorn.normalization.rule.InitialNormalizationRule;
import com.tomtom.places.unicorn.traces.Tracer;

public class VehicleTypesRule implements InitialNormalizationRule {

    @Override
    public NormalizedPlace apply(SourcePlace sourcePlace, NormalizedPlace mappedPlace, Configuration configuration, Tracer tracer) {
        mappedPlace.setVehicleTypes(getDistinctVehicleTypes(sourcePlace));
        populateMediumAndHeavyTruckIfAnyOnePresent(mappedPlace);
        populateDefaultForPetrolStationIfMissing(mappedPlace);
        return mappedPlace;
    }

    private void populateDefaultForPetrolStationIfMissing(NormalizedPlace mappedPlace) {
        if (isPetrolStation(mappedPlace) && doesNotHaveVehicleTypes(mappedPlace)) {
            populateDefaultVehicleTypes(mappedPlace);
        }
    }

    private boolean doesNotHaveVehicleTypes(NormalizedPlace mappedPlace) {
        return mappedPlace.getVehicleTypes().isEmpty();
    }

    private boolean isPetrolStation(NormalizedPlace mappedPlace) {
        return isNotEmpty(mappedPlace.getPetrolFlags());
    }

    private ArrayList<VehicleType> getDistinctVehicleTypes(SourcePlace sourcePlace) {
        if (hasVehicleTypes(sourcePlace))
            return Lists.newArrayList(Sets.newHashSet(sourcePlace.getVehicleTypes()));
        else
            return Lists.newArrayList();
    }

    private boolean hasVehicleTypes(SourcePlace sourcePlace) {
        return sourcePlace.getVehicleTypes() != null;
    }

    private void populateDefaultVehicleTypes(NormalizedPlace mappedPlace) {

        mappedPlace.setVehicleTypes(Lists.<VehicleType>newArrayList());
        for (VehicleType vehicleType : VehicleType.values()) {
            mappedPlace.getVehicleTypes().add(vehicleType);
        }
    }

    private void populateMediumAndHeavyTruckIfAnyOnePresent(NormalizedPlace mappedPlace) {
        if (hasMediumTruck(mappedPlace) && doesNotHaveHeavyTruck(mappedPlace)) {
            mappedPlace.getVehicleTypes().add((VehicleType.HeavyTruck));
        }
        if (hasHeavyTruck(mappedPlace) && doesNotHaveMediumTruck(mappedPlace)) {
            mappedPlace.getVehicleTypes().add((VehicleType.MediumTruck));
        }
    }

    private boolean doesNotHaveMediumTruck(NormalizedPlace mappedPlace) {
        return !hasMediumTruck(mappedPlace);
    }

    private boolean doesNotHaveHeavyTruck(NormalizedPlace mappedPlace) {
        return !hasHeavyTruck(mappedPlace);
    }

    private boolean hasHeavyTruck(NormalizedPlace mappedPlace) {
        return hasSpecifiedVehicleType(mappedPlace, VehicleType.HeavyTruck);
    }

    private boolean hasMediumTruck(NormalizedPlace mappedPlace) {
        return hasSpecifiedVehicleType(mappedPlace, VehicleType.MediumTruck);
    }

    private boolean hasSpecifiedVehicleType(NormalizedPlace mappedPlace, final VehicleType vehicleTypeValue) {
        return Iterables.any(mappedPlace.getVehicleTypes(), new Predicate<VehicleType>() {

            @Override
            public boolean apply(VehicleType attribute) {
                return vehicleTypeValue.equals(attribute);
            }
        });

    }

    @Override
    public String getDescription() {
        return "Copies VehicleTypes to mapped place";
    }
}
