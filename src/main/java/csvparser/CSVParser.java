package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.state.*;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private final CSVColumnSeparator separator;

    public CSVParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public List<List<String>> parse(final String value) {
        ParsingContext context = new ParsingContext(
                new ArrayList<>(),
                new StringBuilder(),
                separator
        );
        ParsingState state = new ColumnStart(context);

        for (int i = 0; i < value.length(); i++) {
            state = state.eval(value.charAt(i));
        }

        return context.grid();
    }
}
