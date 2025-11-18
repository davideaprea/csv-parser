package csvparser.state;

import csvparser.builder.CSVRowBuilder;

public class RowEnd extends ParsingState {
    protected RowEnd(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
