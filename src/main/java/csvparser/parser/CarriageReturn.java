package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

import java.util.ArrayList;
import java.util.List;

class CarriageReturn extends ParsingState {
    public CarriageReturn(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        buildColumn();

        rows.add(new ArrayList<>());

        return getNewColumnStart();
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
