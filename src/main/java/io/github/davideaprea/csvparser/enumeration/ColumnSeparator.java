package io.github.davideaprea.csvparser.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration representing the supported column separator characters.
 */
@AllArgsConstructor
@Getter
public enum ColumnSeparator {
    COMMA(','),
    SEMICOLON(';'),
    TAB('\t'),
    PIPE('|');

    /**
     * The character symbol representing the column separator.
     */
    private final char symbol;
}
