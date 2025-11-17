package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    protected InNormal(StringBuilder stringBuilder, CSVColumnSeparator csvColumnSeparator) {
        super(stringBuilder, csvColumnSeparator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }

        if (character == separator.symbol) {
            return new ColumnEnd(stringBuilder, separator);
        }

        if (character == '\r') {
            return new CarriageReturn(stringBuilder, separator);
        }

        stringBuilder.append(character);

        return this;
    }
}
