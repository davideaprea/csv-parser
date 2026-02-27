package csv.state;

import csv.exception.UnexpectedCharacterException;
import csv.structure.Row;

/**
 * Parsing state representing the encounter
 * of a carriage return character (CR).
 */
public class CarriageReturn extends ParsingState {
    protected CarriageReturn(ParsingContext context) {
        super(context);
    }

    /**
     * @return {@link RowEnd} state, as the CRLF sequence marks the end of the current row.
     * @param character the character to evaluate
     * @throws UnexpectedCharacterException if the input parameter is not a line feed character (LF).
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc4180#section-2">RFC 4180 standard</a> docs about row format
     */
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
