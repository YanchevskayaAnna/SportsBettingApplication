package sportsbetting.exceptions;

public class SportEventIdMismatchException extends RuntimeException{
    public SportEventIdMismatchException() {
        super("Sport event id do not match updated sport event's id");
    }
}
