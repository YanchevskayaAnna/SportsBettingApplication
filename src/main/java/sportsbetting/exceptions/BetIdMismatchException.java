package sportsbetting.exceptions;

public class BetIdMismatchException extends RuntimeException{
    public BetIdMismatchException() {
        super("Bet id do not match updated bet's id");
    }
}
