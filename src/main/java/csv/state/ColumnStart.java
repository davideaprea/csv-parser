package csv.state;

import csv.exception.UnexpectedCharacterException;

/**
 * Parsing state representing the initialization of a new column.
 */
public class ColumnStart extends ParsingState {
    protected ColumnStart(ParsingContext context) {
        super(context);
    }

    /**
     * @throws UnexpectedCharacterException if the given parameter is the line feed character (LF).
     */
    @Override
    public ParsingState eval(final char character) {
        ParsingState nextState = this;

        if (character == '"') {
            nextState = new InQuoted(context);
        } else if (character == context.separator().symbol) {
            context.rowBuilder().endColumn();
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else {
            context.rowBuilder().appendToColumn(character);
            nextState = new InNormal(context);
        }

        return nextState;
    }
}
