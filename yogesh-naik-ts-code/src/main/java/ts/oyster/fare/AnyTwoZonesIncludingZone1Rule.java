package ts.oyster.fare;

import ts.oyster.model.TransportMode;

public class AnyTwoZonesIncludingZone1Rule implements FareRule {

    @Override
    public boolean isApplicable(int fromZone, int toZone, TransportMode mode) {
        return mode == TransportMode.TUBE &&
            (fromZone == 1 && toZone != 1
                || fromZone != 1 && toZone == 1);
    }

    @Override
    public double getFare() {
        return 3.0;
    }

}
