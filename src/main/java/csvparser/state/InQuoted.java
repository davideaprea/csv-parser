package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class InQuoted extends ParsingState {
    protected InQuoted(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '"') {
            return new Escaping(separator, stringBuilder);
        }

        stringBuilder.append(character);

        return this;
    }
}
