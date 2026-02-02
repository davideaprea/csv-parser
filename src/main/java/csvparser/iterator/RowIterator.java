package csvparser.iterator;

import csvparser.builder.RowBuilder;
import csvparser.enumeration.CSVColumnSeparator;
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
    private int currentCharacter;

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

            return current;
        } catch (IOException e) {
            return null;
        }
    }

    private void parseNext() throws IOException {
        if (currentCharacter == -1) {
            currentRow = null;

            return;
        }

        ParsingContext parsingContext = new ParsingContext(
                new RowBuilder(),
                separator
        );
        ParsingState parsingState = new RowInit(parsingContext);

        while (
            !(parsingState instanceof RowEnd) &&
            (currentCharacter = reader.read()) >= 0
        ) {
            parsingState = parsingState.eval((char) currentCharacter);
        }

        currentRow = parsingState.end();
    }
}
