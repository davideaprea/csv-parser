package csvparser.state;

import csvparser.builder.RowBuilder;
import csvparser.enumeration.CSVColumnSeparator;

public record ParsingContext(
        RowBuilder rowBuilder,
        CSVColumnSeparator separator
) {
}
