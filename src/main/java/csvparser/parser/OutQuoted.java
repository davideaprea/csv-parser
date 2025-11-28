package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class OutQuoted extends ParsingState {
    public OutQuoted(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == separator.symbol) {
            buildColumn();

            return getNewColumnStart();
        }
        if (character == '\r') {
            return new CarriageReturn(rows, stringBuilder, separator);
        }
        if (Character.isWhitespace(character)) {
            return this;
        }

        throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
