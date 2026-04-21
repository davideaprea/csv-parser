package io.github.davideaprea.csvparser.exception;

import lombok.Getter;

/**
 * Exception thrown when a row does not match the expected number of columns,
 * as every row in a CSV file must have the same size.
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#page-3">RFC 4180 standard</a>
 */
@Getter
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
}
