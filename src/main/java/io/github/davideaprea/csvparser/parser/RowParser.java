package io.github.davideaprea.csvparser.parser;

import io.github.davideaprea.csvparser.builder.RowBuilder;
import io.github.davideaprea.csvparser.enumeration.ColumnSeparator;
import io.github.davideaprea.csvparser.state.ParsingContext;
import io.github.davideaprea.csvparser.state.ParsingState;
import io.github.davideaprea.csvparser.state.RowEnd;
import io.github.davideaprea.csvparser.state.RowInit;
import io.github.davideaprea.csvparser.model.Row;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

/**
 * Parser responsible for reading characters from a {@link Reader}
 * and converting them into {@link Row} objects.
 * <p>
 * The parser operates in a lazy, streaming fashion: rows are parsed
 * only when {@link #next()} is invoked. This ensures that the entire
 * input is not loaded into memory at once, making the parser suitable
 * for processing large files.
 */
public class RowParser {
    private final Reader input;
    private final ColumnSeparator separator;

    /**
     * Constructs a new instance of this class.
     *
     * @param input     the character input source
     * @param separator the column separator used to split fields
     */
    public RowParser(Reader input, ColumnSeparator separator) {
        this.input = input;
        this.separator = separator;
    }

    /**
     * Parses and returns the next {@link Row} from the input,
     * processing each character individually using a state machine.
     *
     * @return the next parsed {@code Row}, or {@code null} if no more rows exist
     * @throws UncheckedIOException if an I/O error occurs while reading input
     */
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

        if (currentCharacter == -1 && parsedRow.size() == 0) {
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
