package csv.state;

import csv.builder.RowBuilder;
import csv.enumeration.ColumnSeparator;

/**
 * Holds the context configuration of the current parsing session.
 * @param rowBuilder the container for parsed rows
 * @param separator the configured separator used for parsing
 */
public record ParsingContext(
        RowBuilder rowBuilder,
        ColumnSeparator separator
) {
}
