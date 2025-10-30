package csvparser;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfRow;

import java.util.ArrayList;
import java.util.List;

public class CSVColumnBuilder {
    private ParsingState parsingState;
    private List<String> columnValues;
    private long index;
    private char character;
    private StringBuilder stringBuilder;

    private final CSVColumnSeparator separator;

    public CSVColumnBuilder(CSVColumnSeparator separator) {
        this.separator = separator;

        resetState();
    }

    public void evaluate(char character) {
        index++;
        this.character = character;

        if (character == '"') {
            switch (parsingState) {
                case COLUMN_START -> {
                    if(!stringBuilder.isEmpty()) {
                        stringBuilder = new StringBuilder();
                    }

                    parsingState = ParsingState.IN_QUOTED_COLUMN;
                }
                case ESCAPING -> {
                    stringBuilder.append('"');

                    parsingState = ParsingState.IN_QUOTED_COLUMN;
                }
                case IN_QUOTED_COLUMN -> parsingState = ParsingState.ESCAPING;
                case IN_NORMAL_COLUMN, COLUMN_END -> throwUnexpectedCharacter();
            }
        } else if (character == separator.symbol) {
            if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
                stringBuilder.append(separator.symbol);
            } else {
                parsingState = ParsingState.COLUMN_START;

                columnValues.add(stringBuilder.toString());

                stringBuilder = new StringBuilder();
            }
        } else if ((character == '\n' || character == '\r') && parsingState != ParsingState.IN_QUOTED_COLUMN) {
            throwUnexpectedCharacter();
        } else if (Character.isWhitespace(character)) {
            switch (parsingState) {
                case ESCAPING -> parsingState = ParsingState.COLUMN_END;
                case IN_NORMAL_COLUMN, COLUMN_START, IN_QUOTED_COLUMN -> stringBuilder.append(character);
            }
        } else {
            switch (parsingState) {
                case COLUMN_START -> {
                    parsingState = ParsingState.IN_NORMAL_COLUMN;

                    stringBuilder.append(character);
                }
                case IN_QUOTED_COLUMN, IN_NORMAL_COLUMN -> stringBuilder.append(character);
                case ESCAPING, COLUMN_END -> throwUnexpectedCharacter();
            }
        }
    }

    public List<String> build() {
        if (parsingState == ParsingState.IN_QUOTED_COLUMN) {
            resetState();

            throw new UnexpectedEndOfRow("Found an unclosed quoted field.");
        }

        columnValues.add(stringBuilder.toString());

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
        stringBuilder = new StringBuilder();
        parsingState = ParsingState.COLUMN_START;
        index = 0;
        columnValues = new ArrayList<>();
        character = '\0';
    }
}
