package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public final CSVColumnSeparator separator;

    private ParsingState parsingState;
    private List<List<String>> rows;
    private StringBuilder stringBuilder;

    public CSVParser(CSVColumnSeparator separator) {
        this.separator = separator;

        initState();
    }

    public List<List<String>> parse(final String string) {
        for (int i = 0; i < string.length(); i++) {
            parsingState.next(string.charAt(i), this);
        }

        if(!stringBuilder.isEmpty()) {
            buildColumn();
        }

        List<List<String>> result = rows;

        initState();

        return result;
    }

    void setParsingState(ParsingState state) {
        if (state != null) {
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

        if (rows.isEmpty()) {
            addRow();
        }

        rows.getLast().add(column);

        resetColumn();

        parsingState = new ColumnStart();
    }

    void addRow() {
        rows.add(new ArrayList<>());
    }

    void initState() {
        rows = new ArrayList<>();
        parsingState = new ColumnStart();
        stringBuilder = new StringBuilder();
    }
}
