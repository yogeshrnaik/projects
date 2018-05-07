package tdd.examples.trainsignal;

public enum SignalState {

    GREEN ("RED"), RED("YELLOW"), YELLOW("DOUBLE_YELLOW"), DOUBLE_YELLOW("GREEN");

    private String nextState;

    SignalState(String nextState) {
        this.nextState = nextState;
    }

    public SignalState getNextState() {
        return SignalState.valueOf(nextState);
    }
}
