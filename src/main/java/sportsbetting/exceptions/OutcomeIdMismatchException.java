package sportsbetting.exceptions;

public class OutcomeIdMismatchException extends RuntimeException{
    public OutcomeIdMismatchException() {
        super("Outcome id do not match updated outcome's id");
    }
}
