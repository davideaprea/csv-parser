package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.List;

class InQuoted extends ParsingState {
    public InQuoted(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(rows, stringBuilder, separator);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"') {
            return new Escaping(rows, stringBuilder, separator);
        }

        stringBuilder.append(character);

        return this;
    }

    @Override
    List<List<String>> buildGrid() {
        return List.of();
    }
}
