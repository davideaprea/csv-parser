package csvparser.exception;

public class UnexpectedEndOfColumnException extends RuntimeException {
    public UnexpectedEndOfColumnException(String message) {
        super(message);
    }
}
