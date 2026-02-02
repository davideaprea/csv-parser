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
import java.io.UncheckedIOException;
import java.util.Iterator;

public class RowIterator implements Iterator<CSVRow> {
    private final Reader reader;
    private final CSVColumnSeparator separator;

    private CSVRow current;
    private int rowsNumber;

    public RowIterator(Reader reader, CSVColumnSeparator separator) {
        this.reader = reader;
        this.separator = separator;
        rowsNumber = 0;

        next();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public CSVRow next() {
        ParsingContext parsingContext = new ParsingContext(
                new RowBuilder(),
                separator
        );
        ParsingState parsingState = new RowInit(parsingContext);
        int currCharacter;

        try {
            while (
                    !(parsingState instanceof RowEnd) &&
                    (currCharacter = reader.read()) >= 0
            ) {
                parsingState = parsingState.eval((char) currCharacter);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        CSVRow next = parsingContext.rowBuilder().build();

        rowsNumber++;

        if (current != null && current.columnsNumber() != next.columnsNumber()) {
            throw new InvalidRowSizeException(
                    rowsNumber,
                    next.columnsNumber(),
                    current.columnsNumber()
            );
        }

        current = next;

        return current;
    }
}
