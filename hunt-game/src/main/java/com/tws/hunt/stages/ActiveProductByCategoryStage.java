package com.tws.hunt.stages;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import com.tws.hunt.stages.result.CountByCategory;
import com.tws.hunt.stages.result.HuntGameResult;

@Component
public class ActiveProductByCategoryStage extends ActiveProductCountStage {

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected HuntGameResult getCount(JSONArray result) {

        Object collect = result.stream()
            .filter(p -> isActive((Map)p))
            .collect(groupingBy(p -> byCategory((Map)p), counting()));
        System.out.println(collect.toString());
        return new CountByCategory((Map)collect);
    }

    private String byCategory(Map p) {
        return p.get("category").toString();
    }
}
