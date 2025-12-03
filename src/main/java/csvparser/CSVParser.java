package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.state.*;

import java.util.List;

public class CSVParser {
    public List<List<String>> parse(
            final String value,
            final CSVColumnSeparator separator
    ) {
        ParsingContext context = new ParsingContext(separator);

        for (int i = 0; i < value.length(); i++) {
            context.evalCharacter(value.charAt(i));
        }

        return context.end();
    }
}
