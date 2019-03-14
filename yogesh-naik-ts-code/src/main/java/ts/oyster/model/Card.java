package ts.oyster.model;

public class Card {

    private double balance;

    public Card(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void chargeFare(double fare) {
        balance = balance - fare;
    }

    public void revertCharge(double fare) {
        balance = balance + fare;
    }
}
