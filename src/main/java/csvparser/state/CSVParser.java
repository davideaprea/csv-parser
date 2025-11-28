package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private final List<List<String>> rows = new ArrayList<>();
    private final StringBuilder stringBuilder = new StringBuilder();
    public final CSVColumnSeparator separator;

    private ParsingState parsingState = new ColumnStart();

    public CSVParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    void setParsingState(ParsingState state) {
        if(state != null) {
            parsingState = state;
        }
    }

    void addCharacter(char character) {
        stringBuilder.append(character);
    }

    void resetColumn() {
        stringBuilder.setLength(0);
    }

    void buildColumn() {
        final String column = stringBuilder.toString();

        if(rows.isEmpty()) {
            addRow();
        }

        rows.getLast().add(column);

        resetColumn();

        parsingState = new ColumnStart();
    }

    void addRow() {
        rows.add(new ArrayList<>());
    }
}
