package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"') {
            stringBuilder.setLength(0);

            return new InQuoted(stringBuilder);
        }

        if (CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder);
        }

        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        if (Character.isWhitespace(character)) {
            stringBuilder.append(character);

            return this;
        }

        stringBuilder.append(character);

        return new InNormal(stringBuilder);
    }
}
