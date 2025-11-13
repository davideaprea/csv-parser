package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"') {
            stringBuilder.setLength(0);

            return new InQuoted(separator, stringBuilder);
        }

        if (character == separator.symbol) {
            return new ColumnEnd(separator, stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(separator, stringBuilder);
        }

        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        if (Character.isWhitespace(character)) {
            stringBuilder.append(character);

            return this;
        }

        stringBuilder.append(character);

        return new InNormal(separator, stringBuilder);
    }
}
