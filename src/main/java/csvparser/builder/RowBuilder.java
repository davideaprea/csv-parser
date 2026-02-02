package csvparser.builder;

import csvparser.structure.CSVRow;

import java.util.ArrayList;
import java.util.List;

public class RowBuilder {
    private StringBuilder currentColumn = new StringBuilder();
    private List<String> row = new ArrayList<>();

    public void appendToColumn(final char character) {
        currentColumn.append(character);
    }

    public void endColumn() {
        row.add(currentColumn.toString());

        currentColumn = new StringBuilder();
    }

    public CSVRow build() {
        CSVRow csvRow = new CSVRow(row);
        currentColumn = new StringBuilder();
        this.row = new ArrayList<>();

        return csvRow;
    }
}
