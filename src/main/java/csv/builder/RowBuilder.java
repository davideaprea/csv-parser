package csv.builder;

import csv.structure.Row;

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

    public Row build() {
        Row row = new Row(this.row);
        currentColumn = new StringBuilder();
        this.row = new ArrayList<>();

        return row;
    }
}
