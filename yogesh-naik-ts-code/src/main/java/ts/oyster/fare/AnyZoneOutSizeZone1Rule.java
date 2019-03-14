package ts.oyster.fare;

import ts.oyster.model.TransportMode;

public class AnyZoneOutSizeZone1Rule implements FareRule {

    public boolean isApplicable(int fromZone, int toZone, TransportMode mode) {
        return fromZone == 1 && mode == TransportMode.TUBE && toZone != 1;
    }

    public double getFare() {
        return 2.0;
    }
}
