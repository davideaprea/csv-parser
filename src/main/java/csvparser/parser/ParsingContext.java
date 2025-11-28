package csvparser.parser;

import csvparser.enumeration.CSVColumnSeparator;

import java.util.List;

public record ParsingContext(
        List<List<String>> grid,
        StringBuilder stringBuilder,
        CSVColumnSeparator separator
) {
}
