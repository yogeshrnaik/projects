package com.tws.hunt.stages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import com.tws.hunt.stages.result.HuntGameResult;
import com.tws.hunt.stages.result.SimpleCount;

@Component
public class ActiveProductCountStage extends BaseStage {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected HuntGameResult getCount(JSONArray result) {
        return new SimpleCount(result.stream()
            .filter(p -> isActive((Map)p))
            .count());
    }

    @SuppressWarnings("rawtypes")
    protected boolean isActive(Map product) {
        Date startDate = getDate(product, "startDate");
        Date endDate = getDate(product, "endDate");
        boolean isActive = startDate.before(new Date())
            && (endDate == null || endDate.after(new Date()));
        return isActive;
    }

    @SuppressWarnings("rawtypes")
    protected Date getDate(Map product, String dateKey) {
        try {
            return product.get(dateKey) != null ? sdf.parse(product.get(dateKey).toString()) : null;
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date", e);
        }
    }

}
