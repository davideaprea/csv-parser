package csvparser;

import csvparser.builder.CSVRowBuilder;

import java.util.List;

public class CSVParser {
    private final CSVRowBuilder rowBuilder;

    public CSVParser(CSVRowBuilder rowBuilder) {
        this.rowBuilder = rowBuilder;
    }

    public List<String> parse(final String row) {
        for (int i = 0; i < row.length(); i++) {
            rowBuilder.evaluate(row.charAt(i));
        }

        return rowBuilder.build();
    }
}
