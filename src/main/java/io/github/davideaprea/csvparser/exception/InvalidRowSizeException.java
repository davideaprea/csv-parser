package io.github.davideaprea.csvparser.exception;

/**
 * Exception thrown when a row does not match the expected number of columns,
 * as every row in a CSV file must have the same size.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#page-3">RFC 4180 standard</a>
 */
public class InvalidRowSizeException extends RuntimeException {
    private final long rowIndex;
    private final long actualSize;
    private final long expectedSize;

    /**
     * Constructs a new {@code InvalidRowSizeException}.
     *
     * @param rowIndex    the row index where the mismatch occurred
     * @param actualSize   the number of columns actually found
     * @param expectedSize the expected number of columns
     */
    public InvalidRowSizeException(long rowIndex, long actualSize, long expectedSize) {
        super("Every row should have the same number of columns. Invalid row index: " + rowIndex + "; Actual size: " + actualSize + ". Expected size: " + expectedSize + ".");
        this.rowIndex = rowIndex;
        this.actualSize = actualSize;
        this.expectedSize = expectedSize;
    }

    /**
     * @return the invalid row index, 0-based
     */
    public long getRowIndex() {
        return rowIndex;
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

        return exception.rowIndex == rowIndex &&
                exception.actualSize == actualSize &&
                exception.expectedSize == expectedSize;
    }
}
