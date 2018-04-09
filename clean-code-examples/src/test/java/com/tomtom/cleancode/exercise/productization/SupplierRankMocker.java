package com.tomtom.cleancode.exercise.productization;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tomtom.places.unicorn.configuration.SupplierRanking;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank;
import com.tomtom.places.unicorn.configuration.domain.SupplierRank.UseStage;
import com.tomtom.places.unicorn.domain.avro.source.Supplier;

public class SupplierRankMocker {

    private static SupplierRank rank;

    private SupplierRankMocker() {
    }

    /**
     * Mocks a {@link SupplierRanking} returning a default {@link SupplierRank}.
     */
    public static SupplierRanking mockRanking() {
        return mockRanking(false);
    }

    /**
     * Mocks a {@link SupplierRanking} returning a {@link SupplierRank} that {@code hasSuppress}.
     */
    public static SupplierRanking mockRanking(boolean hasSuppress) {
        SupplierRank rank = mockRank(hasSuppress);
        return mockRanking(rank);
    }

    /**
     * Mocks a {@link SupplierRanking} returning a {@link SupplierRank} having {@code addressUseStage}.
     */
    public static SupplierRanking mockRanking(UseStage addressUseStage) {
        return mockRanking(false, addressUseStage);
    }

    /**
     * Mocks a {@link SupplierRanking} returning a {@link SupplierRank} having {@code addressUseStage} and {@code hasSuppress}.
     */
    public static SupplierRanking mockRanking(boolean hasSuppress, UseStage addressUseStage) {
        SupplierRank rank = mockRank(hasSuppress);
        when(rank.getAddressUseStage()).thenReturn(addressUseStage);
        return mockRanking(rank);
    }

    /**
     * Mocks a {@link SupplierRanking} returning the given {@link SupplierRank}.
     */
    @SuppressWarnings("unchecked")
    public static SupplierRanking mockRanking(SupplierRank rank) {
        SupplierRanking supplierRanking = mock(SupplierRanking.class);
        when(supplierRanking.getRank(
            any(Supplier.class), anyCollection(), anyCollection(), anyCollection(),
            any(com.tomtom.places.unicorn.domain.avro.normalized.Locality.class), anyCollection())).thenReturn(rank);
        return supplierRanking;
    }

    /**
     * Mock a {@link SupplierRank} to be returned by the {@link SupplierRanking}.
     */
    public static SupplierRank mockRank(boolean hasSuppress) {
        rank = mock(SupplierRank.class);
        when(rank.hasSuppress()).thenReturn(hasSuppress);
        return rank;
    }

    /**
     * Return the latest generated rank.
     */
    public static SupplierRank getRank() {
        return rank;
    }

}
