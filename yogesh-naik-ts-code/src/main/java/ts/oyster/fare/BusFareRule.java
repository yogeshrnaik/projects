package ts.oyster.fare;

import ts.oyster.model.TransportMode;

public class BusFareRule implements FareRule {

    @Override
    public boolean isApplicable(int fromZone, int toZone, TransportMode mode) {
        return (mode == TransportMode.BUS);
    }

    @Override
    public double getFare() {
        return 1.8;
    }

}
