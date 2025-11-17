package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public abstract class ParsingState {
    protected final StringBuilder stringBuilder;
    protected final CSVColumnSeparator separator;

    protected ParsingState(StringBuilder stringBuilder, CSVColumnSeparator separator) {
        this.stringBuilder = stringBuilder;
        this.separator = separator;
    }

    public abstract ParsingState evalCharacter(final char character);
}
