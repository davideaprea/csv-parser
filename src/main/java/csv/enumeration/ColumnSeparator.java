package csv.enumeration;

/**
 * Enumeration representing the supported column separator characters.
 */
public enum ColumnSeparator {
    COMMA(','),
    SEMICOLON(';'),
    TAB('\t'),
    PIPE('|');

    /**
     * The character symbol representing the column separator.
     */
    public final char symbol;

    ColumnSeparator(final char symbol) {
        this.symbol = symbol;
    }
}
