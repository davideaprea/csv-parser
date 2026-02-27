package csv.state;

import csv.exception.UnexpectedCharacterException;

/**
 * Parsing state representing the encounter
 * of a character in a non-quoted column.
 */
public class InNormal extends ParsingState {
    protected InNormal(ParsingContext context) {
        super(context);
    }

    /**
     * @throws UnexpectedCharacterException if the input parameter is either a {@code "} or a {@code \n} character.
     */
    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = this;

        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (character == context.separator().symbol) {
            context.rowBuilder().endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else {
            context.rowBuilder().appendToColumn(character);
        }

        return nextState;
    }
}
