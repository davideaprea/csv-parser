package csvparser.exception;

public class UnexpectedEndOfRow extends RuntimeException {
    public UnexpectedEndOfRow(String message) {
        super(message);
    }
}
