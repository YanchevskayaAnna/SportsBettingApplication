package sportsbetting.exceptions;

public class WagerIdMismatchException extends RuntimeException{
    public WagerIdMismatchException() {
        super("Wager id do not match updated wager's id");
    }
}
