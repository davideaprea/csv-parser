package csvparser.state;

import java.util.ArrayList;
import java.util.List;

public class CSVGridBuilder {
    private final List<List<String>> rows = new ArrayList<>();

    public void addRow() {
        rows.add(new ArrayList<>());
    }

    public void addColumn(final String column) {
        if(column == null) return;

        if(rows.isEmpty()) {
            addRow();
        }

        rows.getLast().add(column);
    }
}
