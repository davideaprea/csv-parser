package csv.structure;

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
public class Row implements Iterable<String> {
    private final List<String> columns;

    /**
     * Constructs a new instance of this class
     * with the given column values.
     *
     * @param columns the ordered list of column values
     */
    public Row(List<String> columns) {
        this.columns = columns;
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

    /**
     * Compares this row with another object for equality.
     * <p>
     * Two {@code Row} instances are considered equal if they contain
     * the same number of columns and all corresponding column values
     * are equal in the same order.
     *
     * @param obj the object to compare with
     * @return {@code true} if the rows are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Row row)) return false;

        if (size() != row.size()) return false;

        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(row.get(i))) return false;
        }

        return true;
    }
}
