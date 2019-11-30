package com.cleancode.exercise.initialnormalization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copies PaymentMethods from source place to mapped place.
 *
 */
public class PaymentMethodsRule implements InitialNormalizationRule {

    public NormalizedPlace apply(SourcePlace sourcePlace, NormalizedPlace mappedPlace, Configuration configuration, Tracer tracer) {

        List<PaymentMethod> payments = mappedPlace.getPaymentMethods();
        if (payments == null) {
            payments = new ArrayList<PaymentMethod>();
        }

        if (sourcePlace.getPaymentMethodInfos() != null) {
            for (PaymentMethodInfo paymentMethodInfo : sourcePlace.getPaymentMethodInfos()) {
                if (!payments.contains(paymentMethodInfo.getPaymentMethod())) {
                    payments.add(paymentMethodInfo.getPaymentMethod());
                }
            }

        }

        mappedPlace.setPaymentMethods(payments);
        return mappedPlace;
    }

    public String getDescription() {
        return "Set payment methods on normalised place";
    }

}

class SourcePlace{
    public PaymentMethodInfo[] getPaymentMethodInfos() {
        return new PaymentMethodInfo[0];
    }

    public Iterable<VehicleType> getVehicleTypes() {
        return null;
    }
}
class NormalizedPlace {
    public List<PaymentMethod> getPaymentMethods() {
        return null;
    }

    public void setPaymentMethods(List<PaymentMethod> payments) {
    }

    public void setVehicleTypes(ArrayList<VehicleType> distinctVehicleTypes) {

    }

    public Collection getVehicleTypes() {
        return null;
    }

    public Collection getPetrolFlags() {
        return null;
    }
}

class Configuration {}
enum VehicleType{MediumTruck, HeavyTruck}
class Tracer {}
class PaymentMethod {}
class PaymentMethodInfo {
    public PaymentMethod getPaymentMethod() {
        return null;
    }
}
interface InitialNormalizationRule {
    public NormalizedPlace apply(SourcePlace sourcePlace, NormalizedPlace mappedPlace, Configuration configuration, Tracer tracer);
    public String getDescription();
}