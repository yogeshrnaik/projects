package ts.oyster.model;

import static ts.oyster.fare.FareCalculator.MAX_FARE;

import java.util.ArrayList;
import java.util.List;

import ts.oyster.fare.FareCalculator;

public class User {

    private Card card;
    private List<Trip> trips;

    private FareCalculator fareCalculator;

    public User(Card card, FareCalculator fareCalculator) {
        this.card = card;
        trips = new ArrayList<>();
        this.fareCalculator = fareCalculator;
    }

    public void startTrip(Station from, TransportMode mode) {
        if (card.getBalance() < MAX_FARE) {
            throw new InsufficientBalance(String.format("User should have minimum %d balance before taking trip.", MAX_FARE));
        }

        card.chargeFare(MAX_FARE);
        trips.add(new Trip(from, mode, MAX_FARE));
    }

    public void endTrip(Station to) {
        Trip currTrip = trips.get(trips.size() - 1);
        currTrip.setTo(to);
        double fare = fareCalculator.calculateFare(currTrip);
        card.revertCharge(MAX_FARE);
        card.chargeFare(fare);
    }

    public double getBalance() {
        return card.getBalance();
    }
}
