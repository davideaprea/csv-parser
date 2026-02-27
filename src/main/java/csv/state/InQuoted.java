package csv.state;

import csv.exception.UnexpectedCharacterException;
import csv.structure.Row;

/**
 * Parsing state representing the encounter of a character inside a quoted column.
 */
public class InQuoted extends ParsingState {
    protected InQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(context);
        }

        context.rowBuilder().appendToColumn(character);

        return this;
    }

    /**
     * This method will always throw an {@link UnexpectedCharacterException}, since the current column isn't closed.
     *
     * @throws UnexpectedCharacterException everytime this method is called, since the closing {@code "} character is needed
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#page-3">RFC 4180 standard</a> docs about columns format
     */
    @Override
    public Row end() {
        throw new UnexpectedCharacterException('\0', "Unclosed quoted field.");
    }
}
