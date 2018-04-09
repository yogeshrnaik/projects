package com.tomtom.cleancode.exercise.productization;

import static com.tomtom.places.unicorn.productization.gp3.ruleimpl.PaymentMethodsRule.COMPOSITE_PAYMENT_INFO;
import static com.tomtom.places.unicorn.productization.gp3.ruleimpl.PaymentMethodsRule.PAYMENT_METHOD_HIERARCHY;
import static com.tomtom.places.unicorn.productization.gp3.ruleimpl.PaymentMethodsRule.PM_ATRIBUTE_CODE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.domain.avro.archive.ATT;
import com.tomtom.places.unicorn.domain.avro.archive.ArchivePlace;
import com.tomtom.places.unicorn.domain.avro.archive.POI;
import com.tomtom.places.unicorn.domain.avro.clustered.ClusteredPlace;
import com.tomtom.places.unicorn.domain.avro.composite.CompositePlace;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.productized.ProductizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.AVUUID;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethod;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.productization.gp3.GP3Utils;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class PaymentMethodsRuleTest {

    private PaymentMethodsRule paymentMethodsRule;
    private Configuration configuration;
    private TracerMock tracer;
    private ClusteredPlace clusteredPlace;
    private ProductizedPlace productizedPlace;
    private ArchivePlace archivePlace;

    @Before
    public void setUp() {
        SupplierRanking supplierRanking = SupplierRankMocker.mockRanking();

        paymentMethodsRule = new PaymentMethodsRule(supplierRanking);
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
        poi.setGdfFeatureCode("7311");
        poi.setGdfFeatureCodeDesc("Petrol Station");
        archivePlace.setPois(Arrays.asList(poi));

        archivePlace.setAttributes(new ArrayList<ATT>());
    }

    @Test
    public final void testNoPaymentMethod() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        paymentMethodsRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyPaymentMethods(archivePlace);
    }

    @Test
    public final void testPaymentMethodFromSinglePlace() {
        CompositePlace compositePlace = buildDefaultCompositePlace();
        setPaymentMethod(compositePlace, PaymentMethod.BankOrDebitCard);
        clusteredPlace.getMatchingPlaces().add(compositePlace);

        paymentMethodsRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyPaymentMethods(archivePlace, PaymentMethod.BankOrDebitCard);
    }

    @Test
    public final void testPaymentMethodFromMultiplePlaces() {
        CompositePlace compositePlace1 = buildDefaultCompositePlace();

        CompositePlace compositePlace2 = buildDefaultCompositePlace();
        setPaymentMethod(compositePlace2, PaymentMethod.BankOrDebitCard);
        setPaymentMethod(compositePlace2, PaymentMethod.Cash);

        CompositePlace compositePlace3 = buildDefaultCompositePlace();
        setPaymentMethod(compositePlace3, PaymentMethod.CashOnDelivery);

        clusteredPlace.getMatchingPlaces().add(compositePlace1);
        clusteredPlace.getMatchingPlaces().add(compositePlace2);
        clusteredPlace.getMatchingPlaces().add(compositePlace3);

        paymentMethodsRule.apply(archivePlace, clusteredPlace, configuration, tracer);

        verifyPaymentMethods(archivePlace, PaymentMethod.BankOrDebitCard, PaymentMethod.Cash);
    }

    private void verifyPaymentMethods(ArchivePlace place, PaymentMethod... paymentMethods) {
        List<ATT> atts = getAttributes(place, COMPOSITE_PAYMENT_INFO);
        assertEquals(paymentMethods.length, atts.size());

        for (int i = 0; i < paymentMethods.length; i++) {
            assertEquals(PM_ATRIBUTE_CODE, atts.get(i).getAttributeCode());
            assertEquals(PaymentMethodsRule.PAYMENT_METHODS.get(paymentMethods[i]), atts.get(i).getAttributeValue());
            assertEquals(PAYMENT_METHOD_HIERARCHY, atts.get(i).getAttributeHierarchy());
        }
    }

    private CompositePlace buildDefaultCompositePlace() {
        NormalizedPlace mappedPlace = buildDefaultNormalizedPlace();
        NormalizedPlace normalizedPlace = buildDefaultNormalizedPlace();
        return CompositePlace.newBuilder().setMappedPlace(mappedPlace).setNormalizedPlace(normalizedPlace).build();
    }

    private NormalizedPlace buildDefaultNormalizedPlace() {
        return NormalizedPlace.newBuilder().setDeliveryPlaceId(UUIDUtil.newAVUUID(UUID.randomUUID())).build();
    }

    private void setPaymentMethod(CompositePlace compositePlace, PaymentMethod paymentMethod) {
        if (compositePlace.getNormalizedPlace().getPaymentMethods() != null) {
            compositePlace.getNormalizedPlace().getPaymentMethods().add(paymentMethod);
        } else {
            List<PaymentMethod> paymentMethods = Lists.newArrayList(paymentMethod);
            compositePlace.getNormalizedPlace().setPaymentMethods(paymentMethods);
        }

    }

    private List<ATT> getAttributes(ArchivePlace archivePlace, final String attType) {
        List<ATT> attributes = new ArrayList<ATT>(archivePlace.getAttributes());
        CollectionUtils.filter(attributes, new Predicate() {

            @Override
            public boolean evaluate(Object att) {
                return ((ATT)att).getAttributeHierarchy().toString().startsWith(attType);
            }
        });
        return attributes;
    }

}
