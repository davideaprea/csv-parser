package csvparser.parser;

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

public class CSVRowParser {
    private final Reader input;
    private final CSVColumnSeparator separator;

    public CSVRowParser(Reader input, CSVColumnSeparator separator) {
        this.input = input;
        this.separator = separator;
    }

    public CSVRow next() {
        ParsingContext parsingContext = new ParsingContext(
                new RowBuilder(),
                separator
        );
        ParsingState parsingState = new RowInit(parsingContext);
        int currentCharacter = -1;

        while (
                !(parsingState instanceof RowEnd) &&
                (currentCharacter = getNextFromInput()) >= 0
        ) {
            parsingState = parsingState.eval((char) currentCharacter);
        }

        final CSVRow parsedRow = parsingState.end();

        if (currentCharacter == -1 && parsedRow.columnsNumber() == 0) {
            return null;
        }

        return parsedRow;
    }

    private int getNextFromInput() {
        try {
            return input.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
