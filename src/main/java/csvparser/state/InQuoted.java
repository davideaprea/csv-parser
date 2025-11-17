package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class InQuoted extends ParsingState {
    protected InQuoted(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '"') {
            return new Escaping(stringBuilder, separator);
        }

        stringBuilder.append(character);

        return this;
    }
}
