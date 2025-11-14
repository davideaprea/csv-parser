package csvparser.enumeration;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum CSVColumnSeparator {
    COMMA(','),
    SEMICOLON(';'),
    TAB('\t'),
    PIPE('|');

    private static final Set<Character> supportedCharacters;

    public final char symbol;

    static {
        Set<Character> separators = new HashSet<>();

        for (CSVColumnSeparator sep : values()) {
            separators.add(sep.symbol);
        }

        supportedCharacters = Collections.unmodifiableSet(separators);
    }

    CSVColumnSeparator(final char symbol) {
        this.symbol = symbol;
    }

    public static boolean isSeparator(char c) {
        return supportedCharacters.contains(c);
    }
}
