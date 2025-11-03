package csvparser.builder;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder {
    private List<String> columnValues = new ArrayList<>();

    private final CSVColumnBuilder columnBuilder;

    public CSVRowBuilder(CSVColumnBuilder columnBuilder) {
        this.columnBuilder = columnBuilder;
    }

    public void evaluate(char character) {
        if (columnBuilder.isLastColumn()) return;

        if (columnBuilder.isClosed()) {
            columnValues.add(columnBuilder.build());

            columnBuilder.reset();
        }

        columnBuilder.append(character);
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
