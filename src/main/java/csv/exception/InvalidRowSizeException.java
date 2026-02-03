package csv.exception;

public class InvalidRowSizeException extends RuntimeException {
    public final long rowNumber;
    public final long actualSize;
    public final long expectedSize;

    public InvalidRowSizeException(long rowNumber, long actualSize, long expectedSize) {
        super("Every row should have the same number of columns. Invalid row number: " + rowNumber + "; Actual size: " + actualSize + ". Expected size: " + expectedSize + ".");
        this.rowNumber = rowNumber;
        this.actualSize = actualSize;
        this.expectedSize = expectedSize;
    }
}
