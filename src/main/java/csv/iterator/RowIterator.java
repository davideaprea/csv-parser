package csv.iterator;

import csv.exception.InvalidRowSizeException;
import csv.parser.RowParser;
import csv.structure.Row;

import java.util.Iterator;

/**
 * An {@link Iterator} implementation that iterates over {@link Row} objects
 * produced by a {@link RowParser}.
 */
public class RowIterator implements Iterator<Row> {
    private final RowParser rowParser;

    private Row nextRow;
    private int currentRowIndex = -1;

    /**
     * Constructs a new instance, preloading the first row.
     *
     * @param rowParser the parser used to retrieve rows
     */
    public RowIterator(RowParser rowParser) {
        this.rowParser = rowParser;

        next();
    }

    @Override
    public boolean hasNext() {
        return nextRow != null;
    }

    /**
     * @return the next {@code Row}, or {@code null} if no more rows are available
     * @throws InvalidRowSizeException if the next row has a different number
     *                                 of columns than the previous one
     */
    @Override
    public Row next() {
        final Row result = this.nextRow;
        nextRow = rowParser.next();

        currentRowIndex++;

        if (result != null && nextRow != null && nextRow.size() != result.size()) {
            throw new InvalidRowSizeException(
                    currentRowIndex,
                    nextRow.size(),
                    result.size()
            );
        }

        return result;
    }
}
