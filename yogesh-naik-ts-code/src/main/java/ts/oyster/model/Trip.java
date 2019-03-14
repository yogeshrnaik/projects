package ts.oyster.model;

public class Trip {

    private Station from;
    private Station to;
    private TransportMode mode;
    private double fare;

    public Trip(Station from, TransportMode mode, double fare) {
        this.from = from;
        this.mode = mode;
        this.fare = fare;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public TransportMode getMode() {
        return mode;
    }

    public void setMode(TransportMode mode) {
        this.mode = mode;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

}
