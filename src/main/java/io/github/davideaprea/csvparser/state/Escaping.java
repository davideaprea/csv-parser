package io.github.davideaprea.csvparser.state;

import io.github.davideaprea.csvparser.enumeration.ColumnSeparator;
import io.github.davideaprea.csvparser.exception.UnexpectedCharacterException;

/**
 * Parsing state representing the encounter of a double quote character
 * when the current parsing state is {@link InQuoted}.
 */
public class Escaping extends ParsingState {
    protected Escaping(ParsingContext context) {
        super(context);
    }

    /**
     * @throws UnexpectedCharacterException if the given character isn't one of the following: {@code "}, {@code \r}, the configured {@link ColumnSeparator}
     */
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
