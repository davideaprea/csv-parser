package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == separator.symbol) {
            return new ColumnEnd(stringBuilder, separator);
        }

        if(Character.isWhitespace(character)) {
            return new OutQuoted(stringBuilder, separator);
        }

        if(character == '\r') {
            return new CarriageReturn(stringBuilder, separator);
        }

        if(character == '"') {
            stringBuilder.append('"');

            return new InQuoted(stringBuilder, separator);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }
}
