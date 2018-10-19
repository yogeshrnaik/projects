package com.tws.hunt.stages;

import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import com.tws.hunt.stages.result.HuntGameResult;
import com.tws.hunt.stages.result.TotalValueResult;

@Component
public class ActiveProductPriceTotalStage extends ActiveProductCountStage {

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected HuntGameResult getCount(JSONArray result) {
        return new TotalValueResult(
            result.stream()
                .filter(p -> isActive((Map)p))
                .mapToLong(p -> getPrice((Map)p))
                .sum());
    }

    @SuppressWarnings({"rawtypes"})
    private long getPrice(Map p) {
        return Long.parseLong(p.get("price").toString());
    }
}
