package sportsbetting.exceptions;

public class WagerNotFoundException extends RuntimeException{
    public WagerNotFoundException() {
        super("Wager not found");
    }
}
