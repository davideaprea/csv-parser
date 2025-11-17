package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    protected OutQuoted(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n' || !Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }

        if (character == separator.symbol) {
            return new ColumnEnd(stringBuilder, separator);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder, separator);
        }

        return this;
    }
}
