package csvparser.exception;

public class EndOfColumnException extends RuntimeException {
    public EndOfColumnException(String message) {
        super(message);
    }
}
