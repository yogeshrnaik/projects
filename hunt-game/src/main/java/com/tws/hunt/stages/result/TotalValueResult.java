package com.tws.hunt.stages.result;

public class TotalValueResult extends HuntGameResult {

    private final long totalValue;

    public TotalValueResult(long totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toJson() {
        return "{\"totalValue\": " + totalValue + "}";
    }
}
