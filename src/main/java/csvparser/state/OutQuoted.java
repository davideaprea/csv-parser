package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    protected OutQuoted(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n' || !Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }

        if (CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(separator, stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(separator, stringBuilder);
        }

        return this;
    }
}
