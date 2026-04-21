package io.github.davideaprea.csvparser.iterator;

import io.github.davideaprea.csvparser.exception.InvalidRowSizeException;
import io.github.davideaprea.csvparser.parser.RowParser;
import io.github.davideaprea.csvparser.structure.Row;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} implementation that iterates over {@link Row} objects
 * produced by a {@link RowParser}.
 */
public class RowIterator implements Iterator<Row> {
    private final RowParser rowParser;

    private Row currentRow;
    private int currentRowIndex = -1;

    /**
     * Constructs a new instance, preloading the first row.
     *
     * @param rowParser the parser used to retrieve rows
     */
    public RowIterator(RowParser rowParser) {
        this.rowParser = rowParser;

        advance();
    }

    @Override
    public boolean hasNext() {
        return currentRow != null;
    }

    /**
     * @return the next {@code Row}, or {@code null} if no more rows are available
     * @throws InvalidRowSizeException if the next row has a different number
     *                                 of columns than the previous one
     */
    @Override
    public Row next() {
        if (currentRow == null) {
            throw new NoSuchElementException();
        }

        final Row result = currentRow;

        advance();

        if (currentRow != null && currentRow.size() != result.size()) {
            throw new InvalidRowSizeException(
                    currentRowIndex,
                    currentRow.size(),
                    result.size()
            );
        }

        return result;
    }

    private void advance() {
        currentRow = rowParser.next();

        if (currentRow != null) currentRowIndex++;
    }
}
