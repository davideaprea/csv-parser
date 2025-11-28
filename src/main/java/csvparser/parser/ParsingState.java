package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.List;

abstract class ParsingState {
    protected final List<List<String>> rows;
    protected final StringBuilder stringBuilder;
    protected final CSVColumnSeparator separator;

    public ParsingState(List<List<String>> rows, StringBuilder stringBuilder, CSVColumnSeparator separator) {
        this.rows = rows;
        this.stringBuilder = stringBuilder;
        this.separator = separator;
    }

    abstract ParsingState eval(final char character);

    abstract List<List<String>> buildGrid();

    protected void buildColumn() {
        rows.getLast().add(stringBuilder.toString());
    }

    protected ColumnStart getNewColumnStart() {
        return new ColumnStart(rows, new StringBuilder(), separator);
    }
}
