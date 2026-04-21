package io.github.davideaprea.csvparser.model;

import lombok.EqualsAndHashCode;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a single parsed row composed of ordered column values.
 * <p>
 * A {@code Row} is an immutable view over a list of column values
 * produced by the parsing process. Each column can be accessed by
 * its zero-based index.
 * <p>
 * The class implements {@link Iterable}, allowing iteration over
 * column values using enhanced for-loops.
 * <pre>{@code
 * for (String column : row) {
 *     System.out.println(column);
 * }
 * }</pre>
 */
@EqualsAndHashCode
public class Row implements Iterable<String> {
    private final List<String> columns;

    /**
     * Constructs a new instance of this class
     * with the given column values.
     *
     * @param columns the ordered list of column values
     */
    public Row(List<String> columns) {
        this.columns = List.copyOf(columns);
    }

    /**
     * Returns the value of the column at the specified index.
     *
     * @param columnIndex the zero-based column index
     * @return the column value
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public String get(int columnIndex) {
        return columns.get(columnIndex);
    }

    /**
     * Returns the number of columns in this row.
     *
     * @return the column count
     */
    public int size() {
        return columns.size();
    }

    @Override
    public Iterator<String> iterator() {
        return columns.iterator();
    }
}
