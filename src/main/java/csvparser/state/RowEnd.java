package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class RowEnd extends ParsingState {
    protected RowEnd(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        super(stringBuilder, separator);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
