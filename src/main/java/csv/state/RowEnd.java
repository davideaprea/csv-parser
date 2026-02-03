package csv.state;

import csv.exception.UnexpectedCharacterException;
import csv.structure.Row;

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
