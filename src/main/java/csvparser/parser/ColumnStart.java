package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

import java.util.List;

class ColumnStart extends ParsingState {
    public ColumnStart(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(final char character) {
        if (character == '"') {
            return new InQuoted(rows, new StringBuilder());
        }
        if (character == separator.symbol) {
            buildColumn();

            return getNewColumnStart();
        }
        if (character == '\r') {
            return new CarriageReturn(rows, stringBuilder, separator);
        }
        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        stringBuilder.append(character);

        if (Character.isWhitespace(character)) {
            return this;
        }

        return new InNormal(rows, stringBuilder);
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
