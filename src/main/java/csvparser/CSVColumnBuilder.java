package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfColumn;

public class CSVColumnBuilder {
    private ParsingState parsingState = ParsingState.COLUMN_START;
    private StringBuilder stringBuilder = new StringBuilder();

    private final CSVColumnSeparator separator;

    public CSVColumnBuilder(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public CSVColumnBuilder append(char character) {
        if (parsingState == ParsingState.COLUMN_END) {
            return this;
        }

        if (character == '"') {
            evalQuotes();
        } else if (character == separator.symbol) {
            evalSeparator();
        } else if (Character.isWhitespace(character)) {
            evalWhiteSpace(character);
        } else {
            evalNormalCharacter(character);
        }

        return this;
    }

    private void evalQuotes() {
        switch (parsingState) {
            case COLUMN_START -> {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder = new StringBuilder();
                }

                parsingState = ParsingState.IN_QUOTED_COLUMN;
            }
            case ESCAPING -> {
                stringBuilder.append('"');

                parsingState = ParsingState.IN_QUOTED_COLUMN;
            }
            case IN_QUOTED_COLUMN -> parsingState = ParsingState.ESCAPING;
            case IN_NORMAL_COLUMN, OUT_QUOTED_COLUMN -> throw new UnexpectedCharacterException('"');
        }
    }

    private void evalSeparator() {
        if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
            stringBuilder.append(separator.symbol);
        } else {
            parsingState = ParsingState.COLUMN_END;
        }
    }

    private void evalWhiteSpace(final char whiteSpaceChar) {
        if ((whiteSpaceChar == '\n' || whiteSpaceChar == '\r') && parsingState != ParsingState.IN_QUOTED_COLUMN) {
            throw new UnexpectedCharacterException(whiteSpaceChar);
        }

        switch (parsingState) {
            case ESCAPING -> parsingState = ParsingState.OUT_QUOTED_COLUMN;
            case IN_NORMAL_COLUMN, COLUMN_START, IN_QUOTED_COLUMN -> stringBuilder.append(whiteSpaceChar);
        }
    }

    private void evalNormalCharacter(final char character) {
        switch (parsingState) {
            case COLUMN_START -> {
                parsingState = ParsingState.IN_NORMAL_COLUMN;

                stringBuilder.append(character);
            }
            case IN_QUOTED_COLUMN, IN_NORMAL_COLUMN -> stringBuilder.append(character);
            case ESCAPING, COLUMN_END, OUT_QUOTED_COLUMN -> throw new UnexpectedCharacterException(character);
        }
    }

    public boolean isClosed() {
        return parsingState == ParsingState.COLUMN_END;
    }

    public String build() {
        if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
            throw new UnexpectedEndOfColumn("Found an unclosed quoted field.");
        }

        final String columnValue = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        parsingState = ParsingState.COLUMN_START;

        return columnValue;
    }
}
