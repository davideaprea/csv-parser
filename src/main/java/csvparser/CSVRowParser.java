package csvparser;

import java.util.List;

public class CSVRowParser {
    private final CSVRowBuilder rowBuilder;

    public CSVRowParser(CSVRowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }

    public List<String> parse(final String row) {
        for (int i = 0; i < row.length(); i++) {
            rowBuilder.evaluate(row.charAt(i));
        }

        return rowBuilder.build();
    }
}
