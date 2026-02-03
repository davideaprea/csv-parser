package csv.enumeration;

public enum ColumnSeparator {
    COMMA(','),
    SEMICOLON(';'),
    TAB('\t'),
    PIPE('|');

    public final char symbol;

    ColumnSeparator(final char symbol) {
        this.symbol = symbol;
    }
}
