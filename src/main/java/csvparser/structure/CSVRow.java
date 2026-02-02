package csvparser.structure;

import java.util.Iterator;
import java.util.List;

public class CSVRow {
    private final List<String> value;

    public CSVRow(List<String> value) {
        this.value = value;
    }

    public String get(int columnIndex) {
        return value.get(columnIndex);
    }

    public int columnsNumber() {
        return value.size();
    }

    public Iterator<String> getIterator() {
        return value.iterator();
    }
}
