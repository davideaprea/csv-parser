package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder {
    private List<String> columnValues = new ArrayList<>();

    private final CSVColumnBuilder columnBuilder;

    public CSVRowBuilder(CSVColumnSeparator separator) {
        this.columnBuilder = new CSVColumnBuilder(separator);
    }

    public CSVRowBuilder evaluate(char character) {
        if (columnBuilder.isLastColumn()) return this;

        if (columnBuilder.isClosed()) {
            columnValues.add(columnBuilder.build());

            columnBuilder.reset();
        }

        columnBuilder.append(character);

        return this;
    }

    public List<String> build() {
        columnValues.add(columnBuilder.build());

        return columnValues;
    }

    public boolean isRowEnded() {
        return columnBuilder.isLastColumn();
    }

    public void reset() {
        columnBuilder.reset();
        columnValues = new ArrayList<>();
    }
}
