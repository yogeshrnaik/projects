package tdd.examples.trainsignal;

public class Track {
    private Signal firstSignal;
    private Signal lastSignal;

    public Track addSignal(Signal signal) {
        if (firstSignal == null) {
            firstSignal = signal;
            lastSignal = signal;
        } else {
            lastSignal.next = signal;
            signal.previous = lastSignal;
            lastSignal = lastSignal.next;
        }
        return this;
    }

    public void crossSignal(Signal signal) {
        signal.crossSignal();
    }
}
