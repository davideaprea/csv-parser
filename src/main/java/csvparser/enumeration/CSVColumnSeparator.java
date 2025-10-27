package csvparser.enumeration;

public enum CSVColumnSeparator {
    COMMA(','),
    SEMICOLON(';'),
    TAB('\t'),
    PIPE('|');

    public final char symbol;

    CSVColumnSeparator(final char symbol) {
        this.symbol = symbol;
    }
}
