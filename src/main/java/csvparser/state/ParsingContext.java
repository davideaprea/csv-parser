package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public record ParsingContext(
        GridBuilder gridBuilder,
        CSVColumnSeparator separator
) {
}
