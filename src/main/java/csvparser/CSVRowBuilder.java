package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder {
    private ParsingState parsingState;
    private List<String> columnValues;
    private long index;
    private char character;

    private final StringBuilder stringBuilder = new StringBuilder();
    private final CSVColumnSeparator separator;

    public CSVRowBuilder(CSVColumnSeparator separator) {
        this.separator = separator;

        resetState();
    }

    public void evaluate(char character) {
        index++;
        this.character = character;

        if (character == '"') {
            switch (parsingState) {
                case COLUMN_START -> {
                    emptyStringBuilder();

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
                parsingState = ParsingState.COLUMN_START;

                columnValues.add(stringBuilder.toString());

                emptyStringBuilder();
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

    public List<String> build() {
        if (parsingState == ParsingState.IN_QUOTES) {
            resetState();

            throw new UnexpectedEndOfRow("Found an unclosed quoted field.");
        }

        if (!stringBuilder.isEmpty()) {
            columnValues.add(stringBuilder.toString());
        } else if (parsingState == ParsingState.COLUMN_START) {
            columnValues.add("");
        }

        List<String> finalColumnValues = columnValues;

        resetState();

        return finalColumnValues;
    }

    private void throwUnexpectedCharacter() {
        final UnexpectedCharacterException e = new UnexpectedCharacterException(index, character);

        resetState();

        throw e;
    }

    private void resetState() {
        emptyStringBuilder();
        parsingState = ParsingState.COLUMN_START;
        index = 0;
        columnValues = new ArrayList<>();
    }

    private void emptyStringBuilder() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
