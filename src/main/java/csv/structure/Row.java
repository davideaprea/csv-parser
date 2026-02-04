package csv.structure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Row {
    private final List<String> columns;

    public Row(List<String> columns) {
        this.columns = columns;
    }

    public String get(int columnIndex) {
        return columns.get(columnIndex);
    }

    public int size() {
        return columns.size();
    }

    public Iterator<String> iterator() {
        return columns.iterator();
    }
}
