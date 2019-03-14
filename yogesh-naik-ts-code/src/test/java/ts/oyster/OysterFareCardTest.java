package ts.oyster;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ts.oyster.fare.FareCalculator;
import ts.oyster.model.Card;
import ts.oyster.model.Station;
import ts.oyster.model.TransportMode;
import ts.oyster.model.User;

public class OysterFareCardTest {

    private static final double DELTA = 0.000001;
    private User user;
    private FareCalculator fareCalculator;

    @Before
    public void setup() {
        fareCalculator = new FareCalculator();
        user = new User(new Card(30), fareCalculator);
    }

    @Test
    public void whenUserTakesBusTrip_ChargeFlatFare() {
        user.startTrip(new Station("Earl's Court", 1), TransportMode.BUS);
        user.endTrip(new Station("Chelsea", 2));
        assertEquals(28.2, user.getBalance(), DELTA);
    }

    @Test
    public void whenUserTakesTripByTubeWithInZone1_ChargeTwoAndHalf() {
        user.startTrip(new Station("Holborn", 1), TransportMode.TUBE);
        user.endTrip(new Station("Earl's Court", 1));
        assertEquals(27.5, user.getBalance(), DELTA);
    }

    @Test
    public void whenUserTakesTripByTubeAnyOneZoneOutsideZone1_ChargeTwoPound() {
        user.startTrip(new Station("Holborn", 1), TransportMode.TUBE);
        user.endTrip(new Station("Hammersmith", 2));
        assertEquals(28, user.getBalance(), DELTA);
    }

    @Test
    public void whenUserTakesTripByTubeAnyTwoZonesIncludingZone1_ChargeThreePound() {
        user.startTrip(new Station("Hammersmith", 2), TransportMode.TUBE);
        user.endTrip(new Station("Holborn", 1));
        assertEquals(27, user.getBalance(), DELTA);
    }

    @Test
    public void whenUserTakesTripByTubeAnyTwoZonesExcludingZone1_ChargeTwoDotTwentyFive() {
        user.startTrip(new Station("Hammersmith", 2), TransportMode.TUBE);
        user.endTrip(new Station("Wimbledon", 3));
        assertEquals(27.75, user.getBalance(), DELTA);
    }

}
