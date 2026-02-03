package csvparser.iterator;

import csvparser.exception.InvalidRowSizeException;
import csvparser.parser.CSVRowParser;
import csvparser.structure.CSVRow;

import java.io.IOException;
import java.util.Iterator;

public class RowIterator implements Iterator<CSVRow> {
    private final CSVRowParser csvRowParser;

    private CSVRow currentRow;
    private int processedRowsCounter = -1;

    public RowIterator(CSVRowParser csvRowParser) {
        this.csvRowParser = csvRowParser;

        next();
    }

    @Override
    public boolean hasNext() {
        return currentRow != null;
    }

    @Override
    public CSVRow next() {
        try {
            final CSVRow current = this.currentRow;

            currentRow = csvRowParser.next();

            processedRowsCounter++;

            if (areRowsDifferent(current, currentRow)) {
                throw new InvalidRowSizeException(
                        processedRowsCounter,
                        currentRow.columnsNumber(),
                        current.columnsNumber()
                );
            }

            return current;
        } catch (IOException e) {
            return null;
        }
    }

    private boolean areRowsDifferent(CSVRow a, CSVRow b) {
        return a != null && b != null && a.columnsNumber() != b.columnsNumber();
    }
}
