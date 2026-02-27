package csv.structure;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a {@link Row} associated with a header definition,
 * allowing column access by header name instead of only by index.
 * <p>
 * This enables retrieving column values using semantic names.
 */
public class HeadedRow {
    private final Row row;
    private final Map<String, Integer> headers;

    /**
     * Constructs a new instance of this class.
     *
     * @param row     the underlying row containing column values
     * @param headers a mapping between header names and column indices
     */
    public HeadedRow(Row row, Map<String, Integer> headers) {
        this.row = row;
        this.headers = headers;
    }

    /**
     * @return the wrapped row
     */
    public Row getRow() {
        return row;
    }

    /**
     * Returns the value associated with the given header name.
     * <p>
     * If the header name exists, the corresponding column value is returned.
     * Otherwise, an empty {@link Optional} is returned.
     *
     * @param headerName the name of the header
     * @return an {@code Optional} containing the column value,
     * or empty if the header is not defined
     */
    public Optional<String> getByHeaderName(String headerName) {
        return Optional
                .ofNullable(headers.get(headerName))
                .map(row::get);
    }

    /**
     * Compares this {@code HeadedRow} with another object for equality.
     * <p>
     * Two {@code HeadedRow} instances are considered equal if:
     * <ul>
     *     <li>Their underlying {@link Row} instances are equal</li>
     *     <li>All header names in this instance map to equal values
     *         in the compared instance</li>
     * </ul>
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof HeadedRow headedRow)) return false;

        if (!row.equals(headedRow.getRow())) return false;

        return headers
                .entrySet()
                .stream()
                .allMatch(header -> {
                    Optional<String> parameterRowValue = headedRow.getByHeaderName(header.getKey());
                    Optional<String> thisRowValue = getByHeaderName(header.getKey());

                    return parameterRowValue.equals(thisRowValue);
                });
    }
}
