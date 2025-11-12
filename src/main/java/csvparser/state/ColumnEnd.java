package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class ColumnEnd extends ParsingState {
    protected ColumnEnd(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
