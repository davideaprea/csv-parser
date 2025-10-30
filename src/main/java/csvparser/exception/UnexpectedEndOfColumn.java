package csvparser.exception;

public class UnexpectedEndOfColumn extends RuntimeException {
    public UnexpectedEndOfColumn(String message) {
        super(message);
    }
}
