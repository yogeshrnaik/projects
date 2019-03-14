package ts.oyster.fare;

import ts.oyster.model.TransportMode;

public interface FareRule {

    public boolean isApplicable(int fromZone, int toZone, TransportMode mode);

    public double getFare();
}
