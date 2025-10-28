package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;

import java.util.ArrayList;
import java.util.List;

public class CSVRowParser {
    private final CSVColumnSeparator separator;

    public CSVRowParser(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public List<String> parse(final String row) {
        ParsingState parsingState = ParsingState.COLUMN_START;

        final List<String> values = new ArrayList<>();
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < row.length(); i++) {
            final char currChar = row.charAt(i);

            if (currChar == '"') {
                switch (parsingState) {
                    case COLUMN_START -> {
                        stringBuilder.delete(0, stringBuilder.length());

                        parsingState = ParsingState.IN_QUOTES;
                    }
                    case ESCAPING -> {
                        stringBuilder.append('"');

                        parsingState = ParsingState.IN_QUOTES;
                    }
                    case IN_QUOTES -> parsingState = ParsingState.ESCAPING;
                    case OUT_QUOTES, COLUMN_END -> throw new UnexpectedCharacterException(i, currChar);
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
                switch (parsingState) {
                    case ESCAPING -> parsingState = ParsingState.COLUMN_END;
                    case OUT_QUOTES, COLUMN_START, IN_QUOTES -> stringBuilder.append(currChar);
                }
            } else {
                switch (parsingState) {
                    case COLUMN_START -> {
                        parsingState = ParsingState.OUT_QUOTES;

                        stringBuilder.append(currChar);
                    }
                    case IN_QUOTES, OUT_QUOTES -> stringBuilder.append(currChar);
                    case ESCAPING, COLUMN_END -> throw new UnexpectedCharacterException(i, currChar);
                }
            }
        }

        if(parsingState == ParsingState.IN_QUOTES) {
            throw new UnexpectedEndOfRow("Found an unclosed quoted field.");
        }

        if (!stringBuilder.isEmpty()) {
            values.add(stringBuilder.toString());
        }

        return values;
    }
}
