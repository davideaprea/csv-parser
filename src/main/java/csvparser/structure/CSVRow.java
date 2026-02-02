package csvparser.structure;

import java.util.List;

public class CSVRow {
    private final List<String> columns;

    public CSVRow(List<String> columns) {
        this.columns = columns;
    }

    public String get(int columnIndex) {
        return columns.get(columnIndex);
    }

    public int columnsNumber() {
        return columns.size();
    }

    public List<String> getColumns() {
        return List.copyOf(columns);
    }
}
