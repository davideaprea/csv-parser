package csv.exception;

/**
 * Exception thrown when a row does not match the expected number of columns,
 * as every row in a CSV file must have the same size.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#page-3">RFC 4180 standard</a>
 */
public class InvalidRowSizeException extends RuntimeException {
    private final long rowNumber;
    private final long actualSize;
    private final long expectedSize;

    /**
     * Constructs a new {@code InvalidRowSizeException}.
     *
     * @param rowNumber    the number of the row where the mismatch occurred
     * @param actualSize   the number of columns actually found
     * @param expectedSize the expected number of columns
     */
    public InvalidRowSizeException(long rowNumber, long actualSize, long expectedSize) {
        super("Every row should have the same number of columns. Invalid row number: " + rowNumber + "; Actual size: " + actualSize + ". Expected size: " + expectedSize + ".");
        this.rowNumber = rowNumber;
        this.actualSize = actualSize;
        this.expectedSize = expectedSize;
    }

    /**
     * @return the invalid row number
     */
    public long getRowNumber() {
        return rowNumber;
    }

    /**
     * @return the actual column count
     */
    public long getActualSize() {
        return actualSize;
    }

    /**
     * @return the expected column count
     */
    public long getExpectedSize() {
        return expectedSize;
    }

    /**
     * Compares this exception with another object for equality.
     * <p>
     * Two {@code InvalidRowSizeException} instances are considered equal
     * if they have the same row number, actual size, and expected size.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
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
