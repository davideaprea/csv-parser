package csvparser;

import java.util.ArrayList;
import java.util.List;

public class CSVFileParser {
    private ParsingState parsingState = ParsingState.COLUMN_START;

    private final CSVColumnSeparator separator;

    public CSVFileParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public List<String> parse(final String line) {
        final List<String> values = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            final char currChar = line.charAt(i);

            if (currChar == '"') {
                switch (parsingState) {
                    case COLUMN_START -> parsingState = ParsingState.IN_QUOTES;
                    case ESCAPING -> {
                        stringBuilder.append('"');

                        parsingState = ParsingState.IN_QUOTES;
                    }
                    case IN_QUOTES -> parsingState = ParsingState.ESCAPING;
                    case OUT_QUOTES -> throw new RuntimeException(""); //TODO: creare eccezione custom
                }
            } else if (currChar == separator.symbol) {
                if (parsingState == ParsingState.IN_QUOTES) {
                    stringBuilder.append(',');
                } else {
                    parsingState = ParsingState.COLUMN_START;

                    values.add(stringBuilder.toString());

                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else {
                switch (parsingState) {
                    case COLUMN_START -> {
                        parsingState = ParsingState.OUT_QUOTES;

                        stringBuilder.append(currChar);
                    }
                    case IN_QUOTES, OUT_QUOTES -> stringBuilder.append(currChar);
                    case ESCAPING -> throw new RuntimeException(""); //TODO: creare eccezione custom
                }
            }
        }

        if (!stringBuilder.isEmpty()) {
            values.add(stringBuilder.toString());
        }

        return values;
    }
}
