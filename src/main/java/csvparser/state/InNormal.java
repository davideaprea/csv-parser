package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    protected InNormal(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }

        if (CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(separator, stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(separator, stringBuilder);
        }

        stringBuilder.append(character);

        return this;
    }
}
