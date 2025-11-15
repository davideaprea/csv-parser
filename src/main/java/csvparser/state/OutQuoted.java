package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    protected OutQuoted(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n' || !Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }

        if (CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder);
        }

        return this;
    }
}
