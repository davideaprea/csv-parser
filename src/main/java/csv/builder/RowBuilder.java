package csv.builder;

import csv.structure.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class used to incrementally build a {@link Row} instance.
 * <p>
 * The {@code RowBuilder} collects characters into a temporary column buffer
 * and stores completed columns into an internal list. Once all columns have
 * been processed, the {@link #build()} method creates a new {@link Row}
 * instance containing the accumulated column values.
 * <p>
 * This class is stateful and is intended to be used for building a single row
 * at a time. After calling {@link #build()}, the internal state is reset and
 * the builder can be reused to construct another row.
 */
public class RowBuilder {
    private StringBuilder currentColumn = new StringBuilder();
    private List<String> row = new ArrayList<>();

    /**
     * Appends a single character to the current column buffer.
     *
     * @param character the character to append to the current column
     */
    public void appendToColumn(final char character) {
        currentColumn.append(character);
    }

    /**
     * Marks the end of the current column, adding its content to the internal row list.
     * The column buffer is then reset.
     */
    public void endColumn() {
        row.add(currentColumn.toString());

        currentColumn = new StringBuilder();
    }

    /**
     * Builds a new {@link Row} instance using the collected column values.
     * <p>
     * After the {@code Row} is created, the internal state of the builder
     * (both the column buffer and the row list) is reset, allowing the builder
     * to be reused for constructing another row.
     *
     * @return a new {@link Row} containing the accumulated column values
     */
    public Row build() {
        Row row = new Row(this.row);
        currentColumn = new StringBuilder();
        this.row = new ArrayList<>();

        return row;
    }
}
