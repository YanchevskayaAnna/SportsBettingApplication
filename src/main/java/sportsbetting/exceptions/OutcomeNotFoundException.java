package sportsbetting.exceptions;

public class OutcomeNotFoundException extends RuntimeException{
    public OutcomeNotFoundException() {
        super("Bet not found");
    }
}
