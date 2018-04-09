package com.tomtom.cleancode.exercise.productization;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.ImmutableMap;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.domain.avro.archive.ATT;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethod;
import com.tomtom.places.unicorn.productization.gp3.rule.AbstractGP3ProductizationRule;
import com.tomtom.places.unicorn.productization.gp3.ruleimpl.AttRecordBuilder;
import com.tomtom.places.unicorn.productization.gp3.ruleimpl.SupRecordBuilder;
import com.tomtom.places.unicorn.traces.Tracer;

public class PaymentMethodsRule extends AbstractGP3ProductizationRule {

    public static final String COMPOSITE_PAYMENT_INFO = "CPMI";
    public static final String PM_ATRIBUTE_CODE = "TW";
    public static final String PAYMENT_METHOD_HIERARCHY = COMPOSITE_PAYMENT_INFO + "." + PM_ATRIBUTE_CODE;

    public static final Map<PaymentMethod, String> PAYMENT_METHODS = ImmutableMap.<PaymentMethod, String>builder()
        .put(PaymentMethod.BankOrDebitCard, "3")
        .put(PaymentMethod.Cash, "1")
        .put(PaymentMethod.CoinsOnlyOrExactChange, "6")
        .put(PaymentMethod.CreditCard, "2")
        .put(PaymentMethod.ElectronicPurse, "4")
        .put(PaymentMethod.ElectronicTollCollection, "5")
        .put(PaymentMethod.FuelCard, "10")
        .put(PaymentMethod.NoPayment, "0")
        .put(PaymentMethod.ServiceProviderPaymentMethod, "8")
        .put(PaymentMethod.Variable, "7")
        .build();

    public PaymentMethodsRule(SupplierRanking supplierRanking) {
        super(supplierRanking);
    }

    @Override
    public void apply(ArchivePlace archivePlace, ClusteredPlace clusteredPlace, Configuration configuration, Tracer tracer) {
        ArchivePlace oldArchivePlace = ArchivePlace.newBuilder(archivePlace).build();
        for (CompositePlace compositePlace : getCompositePlaces(clusteredPlace)) {
            NormalizedPlace normalizedPlace = compositePlace.getNormalizedPlace();
            populatePaymentMethods(archivePlace, normalizedPlace.getPaymentMethods());

            if (!oldArchivePlace.equals(archivePlace)) {
                // archivePlace has changed
                SupRecordBuilder.addSupRecord(archivePlace, compositePlace);
                // stop when one place has supplied the attribute
                break;
            }
        }
    }

    private void populatePaymentMethods(ArchivePlace archivePlace, List<PaymentMethod> paymentMethods) {

        if (CollectionUtils.isNotEmpty(paymentMethods)) {
            for (PaymentMethod paymentMethod : paymentMethods) {
                populatePaymentAttributes(archivePlace, paymentMethod);
            }
        }
    }

    private void populatePaymentAttributes(ArchivePlace archivePlace, PaymentMethod paymentMethod) {
        String poiUniqueId = archivePlace.getArchivePlaceId().toString();
        String attrId = getAttributeCount(archivePlace.getAttributes());
        AttRecordBuilder.addAttRecord(archivePlace, poiUniqueId, attrId, PM_ATRIBUTE_CODE, PAYMENT_METHODS.get(paymentMethod),
            "", PAYMENT_METHOD_HIERARCHY, attrId);

    }

    private String getAttributeCount(List<ATT> attributes) {
        int attributeCount = 0;

        for (ATT attr : attributes) {
            if (attr.getAttributeHierarchy() != null &&
                attr.getAttributeHierarchy().toString().contains(COMPOSITE_PAYMENT_INFO) &&
                attr.getAttributeHierarchyId() != null) {
                int id = Integer.valueOf(attr.getAttributeHierarchyId().toString());
                if (id > attributeCount) {
                    attributeCount = id;
                }
            }
        }
        return String.valueOf(attributeCount + 1);
    }

    @Override
    public String getDescription() {
        return "Map payment method information.";
    }
}
