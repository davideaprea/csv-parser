package io.github.davideaprea.csvparser.state;

import io.github.davideaprea.csvparser.exception.UnexpectedCharacterException;
import io.github.davideaprea.csvparser.structure.Row;

/**
 * Parsing state representing the end of the current row.
 */
public class RowEnd extends ParsingState {
    protected RowEnd(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        throw new UnexpectedCharacterException(character, "The row is closed");
    }

    @Override
    public Row end() {
        return context.rowBuilder().build();
    }
}
