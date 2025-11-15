package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(stringBuilder);
        }

        if(Character.isWhitespace(character)) {
            return new OutQuoted(stringBuilder);
        }

        if(character == '\r') {
            return new CarriageReturn(stringBuilder);
        }

        if(character == '"') {
            stringBuilder.append('"');

            return new InQuoted(stringBuilder);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }
}
