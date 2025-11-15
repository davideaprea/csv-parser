package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(CSVColumnSeparator.isSeparator(character)) {
            return new ColumnEnd(separator, stringBuilder);
        }

        if(Character.isWhitespace(character)) {
            return new OutQuoted(separator, stringBuilder);
        }

        if(character == '\r') {
            return new CarriageReturn(separator, stringBuilder);
        }

        if(character == '"') {
            stringBuilder.append('"');

            return new InQuoted(separator, stringBuilder);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }
}
