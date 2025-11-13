package csvparser.builder;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.enumeration.ColumnParsingState;
import csvparser.exception.UnexpectedCharacterException;
import csvparser.exception.UnexpectedEndOfColumnException;
import csvparser.state.*;

public class CSVColumnBuilder {
    private ParsingState parsingState;

    private final StringBuilder stringBuilder = new StringBuilder();

    public CSVColumnBuilder(CSVColumnSeparator separator) {
        this.parsingState = new ColumnStart(separator, stringBuilder);
    }

    public void append(char character) {
        if (!isClosed()) {
            parsingState = parsingState.evalCharacter(character);
        }
    }

    public boolean isClosed() {
        return parsingState instanceof ColumnEnd || parsingState instanceof RowEnd;
    }

    public boolean isLast() {
        return parsingState instanceof RowEnd;
    }

    public String build() {
        if (parsingState instanceof InQuoted) {
            throw new UnexpectedEndOfColumnException("Found an unclosed quoted field.");
        }

        return stringBuilder.toString();
    }

    public void reset() {
        stringBuilder.setLength(0);
        parsingState = ColumnParsingState.START;
    }
}
