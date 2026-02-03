package csvparser.state;

import csvparser.builder.RowBuilder;
import csvparser.enumeration.ColumnSeparator;

public record ParsingContext(
        RowBuilder rowBuilder,
        ColumnSeparator separator
) {
}
