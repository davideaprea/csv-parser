package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;

import java.util.ArrayList;
import java.util.List;

public class CSVRowBuilder {
    private ParsingState parsingState = ParsingState.COLUMN_START;
    private long index = 0;
    private List<String> columnValues = new ArrayList<>();

    private final StringBuilder stringBuilder = new StringBuilder();
    private final CSVColumnSeparator separator;

    public CSVRowBuilder(CSVColumnSeparator separator) {
        this.separator = separator;
    }

    public void evaluate(char character) {
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
                case OUT_QUOTES, COLUMN_END -> throw new UnexpectedCharacterException(index, character);
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
            throw new UnexpectedCharacterException(index, character);
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
                case ESCAPING, COLUMN_END -> throw new UnexpectedCharacterException(index, character);
            }
        }

        index++;
    }

    public List<String> build() {
        if (parsingState == ParsingState.IN_QUOTES) {
            throw new UnexpectedEndOfRow("Found an unclosed quoted field.");
        }

        if (!stringBuilder.isEmpty()) {
            columnValues.add(stringBuilder.toString());
        } else if (parsingState == ParsingState.COLUMN_START) {
            columnValues.add("");
        }

        List<String> finalColumnValues = columnValues;

        emptyStringBuilder();
        parsingState = ParsingState.COLUMN_START;
        index = 0;
        columnValues = new ArrayList<>();

        return finalColumnValues;
    }

    private void emptyStringBuilder() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
