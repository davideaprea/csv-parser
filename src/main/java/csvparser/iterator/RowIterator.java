package csvparser.iterator;

import csvparser.exception.InvalidRowSizeException;
import csvparser.parser.CSVRowParser;
import csvparser.structure.CSVRow;

import java.util.Iterator;

public class RowIterator implements Iterator<CSVRow> {
    private final CSVRowParser csvRowParser;

    private CSVRow nextRow;
    private int currentRowIndex = -1;

    public RowIterator(CSVRowParser csvRowParser) {
        this.csvRowParser = csvRowParser;

        next();
    }

    @Override
    public boolean hasNext() {
        return nextRow != null;
    }

    @Override
    public CSVRow next() {
        final CSVRow result = this.nextRow;
        nextRow = csvRowParser.next();

        currentRowIndex++;

        checkNewRowSize(result);

        return result;
    }

    private void checkNewRowSize(CSVRow row) {
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
