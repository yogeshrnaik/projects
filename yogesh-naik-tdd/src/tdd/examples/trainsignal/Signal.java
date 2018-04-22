package tdd.examples.trainsignal;

public class Signal {
    private final String id;
    private SignalState signalState;

    public Signal previous;
    public Signal next;

    public Signal(String id) {
        this.id = id;
        this.signalState = SignalState.GREEN;
    }

    public Signal(String id, SignalState signalState) {
        this.id = id;
        this.signalState = signalState;
    }

    public boolean isRed() {
        return SignalState.RED.equals(signalState);
    }

    public boolean isGreen() {
        return SignalState.GREEN.equals(signalState);
    }

    public boolean isYellow() {
        return SignalState.YELLOW.equals(signalState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Signal signal = (Signal) o;

        return id.equals(signal.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isDoubleYellow() {
        return SignalState.DOUBLE_YELLOW.equals(signalState);
    }

    public void crossSignal() {
        if (isGreen()) {
            updateCrossedSignals();
        }
    }

    private void updateCrossedSignals() {
        Signal signal = this;
        do {
            updateSignalState(signal);
            signal = signal.previous;
        } while (signal != null && !signal.isGreen());
    }

    private void updateSignalState(Signal signal) {
        signal.signalState = signal.signalState.getNextState();
    }
}
