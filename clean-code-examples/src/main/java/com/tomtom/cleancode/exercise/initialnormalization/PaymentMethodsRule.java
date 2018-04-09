package com.tomtom.cleancode.exercise.initialnormalization;

import java.util.ArrayList;
import java.util.List;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethod;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethodInfo;
import com.tomtom.places.unicorn.domain.avro.source.SourcePlace;
import com.tomtom.places.unicorn.normalization.rule.InitialNormalizationRule;
import com.tomtom.places.unicorn.traces.Tracer;

/**
 * Copies PaymentMethods from source place to mapped place.
 * 
 * @author Mindtree
 */
public class PaymentMethodsRule implements InitialNormalizationRule {

    @Override
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

    @Override
    public String getDescription() {
        return "Set payment methods on normalised place";
    }

}
