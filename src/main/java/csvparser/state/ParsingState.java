package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public abstract class ParsingState {
    protected final CSVColumnSeparator separator;
    protected final StringBuilder stringBuilder;

    protected ParsingState(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        this.separator = separator;
        this.stringBuilder = stringBuilder;
    }

    public abstract ParsingState evalCharacter(final char character);
}
