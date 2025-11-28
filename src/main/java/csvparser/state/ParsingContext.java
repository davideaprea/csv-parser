package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.ArrayList;
import java.util.List;

public class ParsingContext {
    private final List<List<String>> rows = new ArrayList<>();
    private final StringBuilder stringBuilder = new StringBuilder();
    public final CSVColumnSeparator separator;

    private ParsingState parsingState = new ColumnStart();

    public ParsingContext(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public void setParsingState(ParsingState state) {
        if(state != null) {
            parsingState = state;
        }
    }

    public void addCharacter(char character) {
        stringBuilder.append(character);
    }

    public void resetColumn() {
        stringBuilder.setLength(0);
    }

    public void buildColumn() {
        final String column = stringBuilder.toString();

        if(rows.isEmpty()) {
            addRow();
        }

        rows.getLast().add(column);

        resetColumn();

        parsingState = new ColumnStart();
    }

    public void addRow() {
        rows.add(new ArrayList<>());
    }
}
