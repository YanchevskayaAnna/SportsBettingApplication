package sportsbetting.exceptions;

public class SportEventNotFoundException extends RuntimeException{
    public SportEventNotFoundException() {
        super("Sport event not found");
    }
}
