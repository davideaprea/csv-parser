package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    protected ColumnStart(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"') {
            stringBuilder.setLength(0);

            return new InQuoted(stringBuilder, separator);
        }

        if (character == separator.symbol) {
            return new ColumnEnd(stringBuilder, separator);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder, separator);
        }

        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        if (Character.isWhitespace(character)) {
            stringBuilder.append(character);

            return this;
        }

        stringBuilder.append(character);

        return new InNormal(stringBuilder, separator);
    }
}
