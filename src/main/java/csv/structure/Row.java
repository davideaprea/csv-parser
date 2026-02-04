package csv.structure;

import java.util.Iterator;
import java.util.List;

public class Row implements Iterable<String> {
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

    @Override
    public Iterator<String> iterator() {
        return columns.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Row row)) return false;

        if (size() != row.size()) return false;

        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(row.get(i))) return false;
        }

        return true;
    }
}
