package csvparser.iterator;

import csvparser.builder.RowBuilder;
import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.InvalidRowSizeException;
import csvparser.state.ParsingContext;
import csvparser.state.ParsingState;
import csvparser.state.RowEnd;
import csvparser.state.RowInit;
import csvparser.structure.CSVRow;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class RowIterator implements Iterator<CSVRow> {
    private final Reader reader;
    private final CSVColumnSeparator separator;

    private CSVRow currentRow;
    private int processedRowsCounter = -1;

    public RowIterator(Reader reader, CSVColumnSeparator separator) {
        this.reader = reader;
        this.separator = separator;

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

            parseNext();

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

    private void parseNext() throws IOException {
        ParsingContext parsingContext = new ParsingContext(
                new RowBuilder(),
                separator
        );
        ParsingState parsingState = new RowInit(parsingContext);
        int currentCharacter = -1;

        while (
                !(parsingState instanceof RowEnd) &&
                (currentCharacter = reader.read()) >= 0
        ) {
            parsingState = parsingState.eval((char) currentCharacter);
        }

        currentRow = parsingState.end();

        if (currentCharacter == -1 && currentRow.columnsNumber() == 0) {
            currentRow = null;
        }
    }

    private boolean areRowsDifferent(CSVRow a, CSVRow b) {
        return a != null && b != null && a.columnsNumber() != b.columnsNumber();
    }
}
