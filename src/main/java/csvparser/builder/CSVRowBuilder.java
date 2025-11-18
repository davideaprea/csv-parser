package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.state.ColumnStart;
import csvparser.state.ParsingState;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder {
    private final List<List<String>> rows;
    private final CSVColumnSeparator separator;
    private final StringBuilder columnBuilder;

    private ParsingState parsingState;

    public CSVRowBuilder(CSVColumnSeparator separator) {
        this.separator = separator;
        this.columnBuilder = new StringBuilder();
        rows = new ArrayList<>();
        parsingState = new ColumnStart(this);
    }

    public boolean isSeparator(final char character) {
        return character == separator.symbol;
    }

    public void addRow() {
        rows.add(new ArrayList<>());
    }

    public void buildColumn() {
        rows.getLast().add(columnBuilder.toString());
    }

    public void addCharacter(char character) {
        columnBuilder.append(character);
    }

    public void resetColumn() {
        columnBuilder.setLength(0);
    }
}
