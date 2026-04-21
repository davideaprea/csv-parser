package io.github.davideaprea.csvparser.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * Represents a {@link Row} associated with a header definition,
 * allowing column access by header name instead of only by index.
 * <p>
 * This enables retrieving column values using semantic names.
 */
@Getter
@EqualsAndHashCode
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
        this.headers = Map.copyOf(headers);
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
}
