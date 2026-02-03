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
        final CSVRow previous = this.nextRow;
        nextRow = csvRowParser.next();

        currentRowIndex++;

        checkNewRowSize(previous);

        return previous;
    }

    private void checkNewRowSize(CSVRow newRow) {
        final boolean areRowSizesDifferent =
                newRow != null &&
                nextRow != null &&
                newRow.columnsNumber() != nextRow.columnsNumber();

        if (areRowSizesDifferent) {
            throw new InvalidRowSizeException(
                    currentRowIndex,
                    nextRow.columnsNumber(),
                    newRow.columnsNumber()
            );
        }
    }
}
