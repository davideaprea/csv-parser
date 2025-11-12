package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class RowEnd extends ParsingState {
    protected RowEnd(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
