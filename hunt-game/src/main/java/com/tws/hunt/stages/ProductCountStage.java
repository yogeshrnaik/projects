package com.tws.hunt.stages;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class ProductCountStage extends BaseStage {

    protected long getCount(JSONArray result) {
        return result.size();
    }
}
