package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    protected InNormal(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }

        if (CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(stringBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder);
        }

        stringBuilder.append(character);

        return this;
    }
}
