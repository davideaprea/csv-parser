package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;

public class Temp {
    private ParsingState parsingState;
    private StringBuilder stringBuilder;
    private char character;

    private final CSVColumnSeparator separator;

    public Temp(CSVColumnSeparator separator) {
        this.separator = separator;

        resetState();
    }

    public void append(char character) {
        this.character = character;

        if (parsingState == ParsingState.COLUMN_END) {
            throwUnexpectedCharacter();
        }

        if (character == '"') {
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
                case IN_NORMAL_COLUMN, OUT_QUOTED_COLUMN -> throwUnexpectedCharacter();
            }
        } else if (character == separator.symbol) {
            if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
                stringBuilder.append(separator.symbol);
            } else {
                parsingState = ParsingState.COLUMN_END;
            }
        } else if ((character == '\n' || character == '\r') && parsingState != ParsingState.IN_QUOTED_COLUMN) {
            throwUnexpectedCharacter();
        } else if (Character.isWhitespace(character)) {
            switch (parsingState) {
                case ESCAPING -> parsingState = ParsingState.OUT_QUOTED_COLUMN;
                case IN_NORMAL_COLUMN, COLUMN_START, IN_QUOTED_COLUMN -> stringBuilder.append(character);
            }
        } else {
            switch (parsingState) {
                case COLUMN_START -> {
                    parsingState = ParsingState.IN_NORMAL_COLUMN;

                    stringBuilder.append(character);
                }
                case IN_QUOTED_COLUMN, IN_NORMAL_COLUMN -> stringBuilder.append(character);
                case ESCAPING, COLUMN_END, OUT_QUOTED_COLUMN -> throwUnexpectedCharacter();
            }
        }
    }

    public boolean isClosed() {
        return parsingState == ParsingState.COLUMN_END;
    }

    @Override
    public String toString() {
        final String columnValue = stringBuilder.toString();

        resetState();

        if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
            throw new UnexpectedEndOfRow("Found an unclosed quoted field.");
        }

        return columnValue;
    }

    private void throwUnexpectedCharacter() {
        final UnexpectedCharacterException e = new UnexpectedCharacterException(character);

        resetState();

        throw e;
    }

    private void resetState() {
        stringBuilder = new StringBuilder();
        parsingState = ParsingState.COLUMN_START;
        character = '\0';
    }
}
