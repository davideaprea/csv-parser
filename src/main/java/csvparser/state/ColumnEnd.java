package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class ColumnEnd extends ParsingState {
    protected ColumnEnd(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
