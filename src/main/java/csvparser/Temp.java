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

        if(parsingState == ParsingState.COLUMN_END) {
            throwUnexpectedCharacter();
        }

        if (character == '"') {
            switch (parsingState) {
                case COLUMN_START -> {
                    if(!stringBuilder.isEmpty()) {
                        stringBuilder = new StringBuilder();
                    }

                    parsingState = ParsingState.IN_QUOTES;
                }
                case ESCAPING -> {
                    stringBuilder.append('"');

                    parsingState = ParsingState.IN_QUOTES;
                }
                case IN_QUOTES -> parsingState = ParsingState.ESCAPING;
                case OUT_QUOTES, COLUMN_END -> throwUnexpectedCharacter();
            }
        } else if (character == separator.symbol) {
            if (parsingState == ParsingState.IN_QUOTES) {
                stringBuilder.append(separator.symbol);
            } else {
                parsingState = ParsingState.COLUMN_END;
            }
        } else if ((character == '\n' || character == '\r') && parsingState != ParsingState.IN_QUOTES) {
            throwUnexpectedCharacter();
        } else if (Character.isWhitespace(character)) {
            switch (parsingState) {
                case ESCAPING -> parsingState = ParsingState.COLUMN_END;
                case OUT_QUOTES, COLUMN_START, IN_QUOTES -> stringBuilder.append(character);
            }
        } else {
            switch (parsingState) {
                case COLUMN_START -> {
                    parsingState = ParsingState.OUT_QUOTES;

                    stringBuilder.append(character);
                }
                case IN_QUOTES, OUT_QUOTES -> stringBuilder.append(character);
                case ESCAPING, COLUMN_END -> throwUnexpectedCharacter();
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

        if (parsingState == ParsingState.IN_QUOTES) {
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
