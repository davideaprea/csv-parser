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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof InvalidRowSizeException exception)) {
            return false;
        }

        return exception.rowNumber == rowNumber &&
                exception.actualSize == actualSize &&
                exception.expectedSize == expectedSize;
    }
}
