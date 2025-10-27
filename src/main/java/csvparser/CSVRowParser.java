package csvparser;

import java.util.ArrayList;
import java.util.List;

public class CSVRowParser {
    private ParsingState parsingState = ParsingState.COLUMN_START;

    private final CSVColumnSeparator separator;

    public CSVRowParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public List<String> parse(final String row) {
        final List<String> values = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < row.length(); i++) {
            final char currChar = row.charAt(i);

            if (currChar == '"') {
                switch (parsingState) {
                    case COLUMN_START -> parsingState = ParsingState.IN_QUOTES;
                    case ESCAPING -> {
                        stringBuilder.append('"');

                        parsingState = ParsingState.IN_QUOTES;
                    }
                    case IN_QUOTES -> parsingState = ParsingState.ESCAPING;
                    case OUT_QUOTES -> throw new UnexpectedCharacterException(i, currChar);
                }
            } else if (currChar == separator.symbol) {
                if (parsingState == ParsingState.IN_QUOTES) {
                    stringBuilder.append(separator.symbol);
                } else {
                    parsingState = ParsingState.COLUMN_START;

                    values.add(stringBuilder.toString());

                    stringBuilder.delete(0, stringBuilder.length());
                }
            } else if ((currChar == '\n' || currChar == '\r') && parsingState != ParsingState.IN_QUOTES) {
                throw new UnexpectedCharacterException(i, currChar);
            } else if (Character.isWhitespace(currChar)) {
                if (parsingState == ParsingState.ESCAPING) {
                    throw new UnexpectedCharacterException(i, currChar);
                }

                stringBuilder.append(currChar);
            } else {
                switch (parsingState) {
                    case COLUMN_START -> {
                        parsingState = ParsingState.OUT_QUOTES;

                        stringBuilder.append(currChar);
                    }
                    case IN_QUOTES, OUT_QUOTES -> stringBuilder.append(currChar);
                    case ESCAPING -> throw new UnexpectedCharacterException(i, currChar);
                }
            }
        }

        if (!stringBuilder.isEmpty()) {
            values.add(stringBuilder.toString());
        }

        return values;
    }
}
