package csv.parser;

import csv.builder.RowBuilder;
import csv.enumeration.ColumnSeparator;
import csv.state.ParsingContext;
import csv.state.ParsingState;
import csv.state.RowEnd;
import csv.state.RowInit;
import csv.structure.Row;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class RowParser {
    private final Reader input;
    private final ColumnSeparator separator;

    public RowParser(Reader input, ColumnSeparator separator) {
        this.input = input;
        this.separator = separator;
    }

    public Row next() {
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

        final Row parsedRow = parsingState.end();

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
