package com.tomtom.cleancode.exercise.initialnormalization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.tomtom.places.unicorn.configuration.Configuration;
import com.tomtom.places.unicorn.domain.avro.normalized.NormalizedPlace;
import com.tomtom.places.unicorn.domain.avro.source.AVUUID;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethod;
import com.tomtom.places.unicorn.domain.avro.source.PaymentMethodInfo;
import com.tomtom.places.unicorn.domain.avro.source.SourcePlace;
import com.tomtom.places.unicorn.domain.util.UUIDUtil;
import com.tomtom.places.unicorn.testutil.TracerMock;

public class PaymentMethodsRuleTest {

    private PaymentMethodsRule rule;
    private SourcePlace sourcePlace;
    private TracerMock tracer;
    private Configuration configuration;
    private static final AVUUID DELIVERY_PLACE_ID_1 = UUIDUtil.newAVUUID(UUID.randomUUID());

    @Before
    public void setUp() {
        rule = new PaymentMethodsRule();
        tracer = new TracerMock();
        configuration = mock(Configuration.class);

    }

    @Test
    public void testApply() {
        sourcePlace = buildPaymentMethodSourcePlace();
        NormalizedPlace mappedPlace = NormalizedPlace.newBuilder().setDeliveryPlaceId(DELIVERY_PLACE_ID_1).build();
        assertEquals(null, mappedPlace.getPaymentMethods());

        NormalizedPlace resultPlace = rule.apply(sourcePlace, mappedPlace, configuration, tracer);

        assertEquals(2, resultPlace.getPaymentMethods().size());
        assertEquals(PaymentMethod.Cash, resultPlace.getPaymentMethods().get(0));
        assertEquals(PaymentMethod.Invoice, resultPlace.getPaymentMethods().get(1));
    }

    @Test
    public void testApplyExistingPaymentMethods() {
        // Test adding payment methods to existing methods in the mapped place
        sourcePlace = buildPaymentMethodSourcePlace();

        List<PaymentMethod> list = new ArrayList<PaymentMethod>();
        list.add(PaymentMethod.CashOnDelivery);
        list.add(PaymentMethod.Cash);
        NormalizedPlace mappedPlace = NormalizedPlace.newBuilder().setDeliveryPlaceId(DELIVERY_PLACE_ID_1).setPaymentMethods(list).build();

        assertEquals(PaymentMethod.CashOnDelivery, mappedPlace.getPaymentMethods().get(0));
        assertEquals(PaymentMethod.Cash, mappedPlace.getPaymentMethods().get(1));

        NormalizedPlace resultPlace = rule.apply(sourcePlace, mappedPlace, configuration, tracer);

        assertEquals(3, resultPlace.getPaymentMethods().size());
        assertEquals(PaymentMethod.CashOnDelivery, resultPlace.getPaymentMethods().get(0));
        assertEquals(PaymentMethod.Cash, resultPlace.getPaymentMethods().get(1));
        assertEquals(PaymentMethod.Invoice, resultPlace.getPaymentMethods().get(2));
    }

    @Test
    public void testApplyNoPaymentMethodInput() {
        sourcePlace = SourcePlace.newBuilder().setDeliveryPlaceId(DELIVERY_PLACE_ID_1).build();
        NormalizedPlace mappedPlace = NormalizedPlace.newBuilder().setDeliveryPlaceId(DELIVERY_PLACE_ID_1).build();
        assertEquals(null, mappedPlace.getPaymentMethods());

        NormalizedPlace resultPlace = rule.apply(sourcePlace, mappedPlace, configuration, tracer);

        assertNotNull(resultPlace.getPaymentMethods());
        assertEquals(0, resultPlace.getPaymentMethods().size());
    }

    private SourcePlace buildPaymentMethodSourcePlace() {
        List<PaymentMethodInfo> paymentMethodInfoList = new ArrayList<PaymentMethodInfo>();
        paymentMethodInfoList.add(PaymentMethodInfo.newBuilder().setPaymentMethod(PaymentMethod.Cash).build());
        paymentMethodInfoList.add(PaymentMethodInfo.newBuilder().setPaymentMethod(PaymentMethod.Invoice).build());
        paymentMethodInfoList.add(PaymentMethodInfo.newBuilder().setPaymentMethod(PaymentMethod.Invoice).build());

        return SourcePlace.newBuilder()
            .setDeliveryPlaceId(DELIVERY_PLACE_ID_1)
            .setPaymentMethodInfos(paymentMethodInfoList)
            .build();
    }

}
