package csvparser.iterator;

import csvparser.exception.InvalidRowSizeException;
import csvparser.parser.RowParser;
import csvparser.structure.Row;

import java.util.Iterator;

public class RowIterator implements Iterator<Row> {
    private final RowParser rowParser;

    private Row nextRow;
    private int currentRowIndex = -1;

    public RowIterator(RowParser rowParser) {
        this.rowParser = rowParser;

        next();
    }

    @Override
    public boolean hasNext() {
        return nextRow != null;
    }

    @Override
    public Row next() {
        final Row result = this.nextRow;
        nextRow = rowParser.next();

        currentRowIndex++;

        checkNewRowSize(result);

        return result;
    }

    private void checkNewRowSize(Row row) {
        final boolean areRowSizesDifferent =
                row != null &&
                nextRow != null &&
                row.columnsNumber() != nextRow.columnsNumber();

        if (areRowSizesDifferent) {
            throw new InvalidRowSizeException(
                    currentRowIndex,
                    nextRow.columnsNumber(),
                    row.columnsNumber()
            );
        }
    }
}
