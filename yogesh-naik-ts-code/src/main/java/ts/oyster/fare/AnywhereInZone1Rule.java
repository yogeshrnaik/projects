package ts.oyster.fare;

import ts.oyster.model.TransportMode;

public class AnywhereInZone1Rule implements FareRule {

    @Override
    public boolean isApplicable(int fromZone, int toZone, TransportMode mode) {
        return fromZone == 1 && toZone == 1 && mode == TransportMode.TUBE;
    }

    @Override
    public double getFare() {
        return 2.5;
    }

}
