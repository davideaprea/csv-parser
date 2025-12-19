package csvparser.state;

import csvparser.builder.GridBuilder;
import csvparser.enumeration.CSVColumnSeparator;

public record ParsingContext(
        GridBuilder gridBuilder,
        CSVColumnSeparator separator
) {
}
