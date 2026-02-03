package csv.state;

import csv.exception.UnexpectedCharacterException;
import csv.structure.Row;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        context.rowBuilder().endColumn();

        return new RowEnd(context);
    }

    @Override
    public Row end() {
        throw new UnexpectedCharacterException('\0', "Expected LF character");
    }
}
