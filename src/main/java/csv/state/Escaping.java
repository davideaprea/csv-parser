package csv.state;

import csv.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState;

        if (character == context.separator().symbol) {
            context.rowBuilder().endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '"') {
            context.rowBuilder().appendToColumn(character);
            nextState = new InQuoted(context);
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }

        return nextState;
    }
}
