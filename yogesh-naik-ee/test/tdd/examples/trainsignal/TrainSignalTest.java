package tdd.examples.trainsignal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrainSignalTest {

    @Test
    public void signalShouldTurnRedWhenTrainCrossesIt() {
        Signal signal0 = new Signal("S0");
        Track track = new Track().addSignal(signal0);

        track.crossSignal(signal0);

        assertTrue(signal0.isRed());
    }

    @Test
    public void trainCrossesSecondSignal() {
        Signal signal0 = new Signal("S0", SignalState.RED);
        Signal signal1 = new Signal("S1", SignalState.GREEN);
        Track track = new Track().addSignal(signal0).addSignal(signal1);

        track.crossSignal(signal1);

        assertTrue(signal0.isYellow());
        assertTrue(signal1.isRed());
    }

    @Test
    public void trainCrossesTwoSignals() {
        Signal signal0 = new Signal("S0");
        Signal signal1 = new Signal("S1");
        Track track = new Track().addSignal(signal0).addSignal(signal1);

        track.crossSignal(signal0);
        track.crossSignal(signal1);

        assertTrue(signal0.isYellow());
        assertTrue(signal1.isRed());
    }

    @Test
    public void trainMovesThreeSignals() {
        Signal signal0 = new Signal("S0");
        Signal signal1 = new Signal("S1");
        Signal signal2 = new Signal("S2");
        Track track = new Track().addSignal(signal0).addSignal(signal1).addSignal(signal2);

        track.crossSignal(signal0);
        track.crossSignal(signal1);
        track.crossSignal(signal2);

        assertTrue(signal0.isDoubleYellow());
        assertTrue(signal1.isYellow());
        assertTrue(signal2.isRed());
    }

    @Test
    public void trainMovesFourSignals() {
        Signal signal0 = new Signal("S0");
        Signal signal1 = new Signal("S1");
        Signal signal2 = new Signal("S2");
        Signal signal3 = new Signal("S3");
        Track track = new Track().addSignal(signal0).addSignal(signal1).addSignal(signal2).addSignal(signal3);

        track.crossSignal(signal0);
        track.crossSignal(signal1);
        track.crossSignal(signal2);
        track.crossSignal(signal3);

        assertTrue(signal0.isGreen());
        assertTrue(signal1.isDoubleYellow());
        assertTrue(signal2.isYellow());
        assertTrue(signal3.isRed());
    }
}
