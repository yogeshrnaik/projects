package ts.oyster.model;

public class InsufficientBalance extends RuntimeException {

    private static final long serialVersionUID = 8965552150189620405L;

    public InsufficientBalance(String msg) {
        super(msg);
    }

}
