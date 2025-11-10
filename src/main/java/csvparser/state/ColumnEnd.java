package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class ColumnEnd extends ParsingState {
    protected ColumnEnd(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        return this;
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        return this;
    }

    @Override
    protected ParsingState evalLineFeed() {
        return this;
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        return this;
    }

    @Override
    protected ParsingState evalSeparator() {
        return this;
    }

    @Override
    protected ParsingState evalQuotes() {
        return this;
    }
}
