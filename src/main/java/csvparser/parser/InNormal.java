package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class InNormal extends ParsingState {
    public InNormal(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }
        if (character == separator.symbol) {
            buildColumn();

            return getNewColumnStart();
        }
        if (character == '\r') {
            return new CarriageReturn(rows, stringBuilder, separator);
        }

        stringBuilder.append(character);

        return this;
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
