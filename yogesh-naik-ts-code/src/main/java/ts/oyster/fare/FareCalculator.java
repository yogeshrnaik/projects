package ts.oyster.fare;

import java.util.ArrayList;
import java.util.List;

import ts.oyster.model.Trip;

public class FareCalculator {

    public static final double MAX_FARE = 3.2;
    private List<FareRule> fareRules;

    public FareCalculator() {
        fareRules = new ArrayList<>();
        fareRules.add(new BusFareRule());
        fareRules.add(new AnywhereInZone1Rule());
        fareRules.add(new AnyZoneOutSizeZone1Rule());
        fareRules.add(new AnyTwoZonesIncludingZone1Rule());
        fareRules.add(new AnyTwoZonesExcludingZone1Rule());
    }

    public double calculateFare(Trip trip) {
        double fare = MAX_FARE;
        for (FareRule rule : fareRules) {
            if (isApplicable(rule, trip)) {
                fare = Math.min(rule.getFare(), fare);
            }
        }

        return fare;
    }

    private boolean isApplicable(FareRule rule, Trip trip) {
        for (Integer fromZone : trip.getFrom().getZones()) {
            for (Integer toZone : trip.getTo().getZones()) {
                if (rule.isApplicable(fromZone, toZone, trip.getMode())) {
                    return true;
                }
            }
        }
        return false;
    }
}
