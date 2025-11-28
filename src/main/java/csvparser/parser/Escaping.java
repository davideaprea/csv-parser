package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class Escaping extends ParsingState {
    public Escaping(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == separator.symbol) {
            buildColumn();

            return getNewColumnStart();
        }
        if (Character.isWhitespace(character)) {
            return new OutQuoted(rows, stringBuilder);
        }
        if (character == '\r') {
            return new CarriageReturn(rows, stringBuilder, separator);
        }
        if (character == '"') {
            stringBuilder.append(character);

            return new InQuoted(rows, stringBuilder);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
