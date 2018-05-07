package tdd.examples.cricket;

public class Player {

    private int points;

    private int runs;
    private int wickets;
    private int catches;

    public Player(int runs, int wickets, int catches) {
        this.runs = runs;
        this.wickets = wickets;
        this.catches = catches;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getCatches() {
        return catches;
    }

    public void setCatches(int catches) {
        this.catches = catches;
    }
}
