package csv.state;

import csv.builder.RowBuilder;
import csv.enumeration.ColumnSeparator;

public record ParsingContext(
        RowBuilder rowBuilder,
        ColumnSeparator separator
) {
}
