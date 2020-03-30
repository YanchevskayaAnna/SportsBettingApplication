package sportsbetting.exceptions;

public class BetNotFoundException extends RuntimeException{
    public BetNotFoundException() {
        super("Bet not found");
    }
}
