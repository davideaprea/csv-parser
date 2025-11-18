package csvparser.state;

import csvparser.builder.CSVRowBuilder;

public abstract class ParsingState {
    protected final CSVRowBuilder rowBuilder;

    protected ParsingState(CSVRowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }

    public abstract ParsingState evalCharacter(final char character);
}
