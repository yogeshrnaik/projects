package com.tws.hunt.stages;

import org.apache.http.HttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class ProductCountStage extends BaseStage {

    protected int getCount(HttpResponse res) throws Exception {
        String resp = readResponse(res);
        System.out.println("Response : " + resp);
        JSONArray obj = (JSONArray)new JSONParser().parse(resp);
        return obj.size();
    }
}
