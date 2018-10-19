package com.tws.hunt.stages;

import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import com.tws.hunt.stages.result.HuntGameResult;
import com.tws.hunt.stages.result.SimpleCount;

@Component
public class ActiveProductByCategoryStage extends ActiveProductCountStage {

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected HuntGameResult getCount(JSONArray result) {
        return new SimpleCount(result.stream()
            .filter(p -> isActive((Map)p)).count());
    }
}
