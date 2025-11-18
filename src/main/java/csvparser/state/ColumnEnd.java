package csvparser.state;

import csvparser.builder.CSVRowBuilder;

public class ColumnEnd extends ParsingState {
    public ColumnEnd(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        return this;
    }
}
