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
import java.io.UncheckedIOException;
import java.util.Iterator;

public class RowIterator implements Iterator<CSVRow> {
    private final Reader reader;
    private final CSVColumnSeparator separator;

    private CSVRow current;

    public RowIterator(Reader reader, CSVColumnSeparator separator) {
        this.reader = reader;
        this.separator = separator;

        next();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public CSVRow next() {
        try {
            final CSVRow current = this.current;
            this.current = parseNext(reader);

            return current;
        } catch (IOException e) {
            return null;
        }
    }

    private CSVRow parseNext(Reader reader) throws IOException {
        ParsingContext parsingContext = new ParsingContext(
                new RowBuilder(),
                separator
        );
        ParsingState parsingState = new RowInit(parsingContext);
        int currCharacter = reader.read();

        if (currCharacter == -1) return null;

        while (
                !(parsingState instanceof RowEnd) &&
                currCharacter >= 0
        ) {
            parsingState = parsingState.eval((char) currCharacter);
            currCharacter = reader.read();
        }

        return parsingState.end();
    }
}
