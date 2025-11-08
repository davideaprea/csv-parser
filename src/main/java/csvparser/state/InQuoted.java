package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class InQuoted extends ParsingState {
    protected InQuoted(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        return null;
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        return null;
    }

    @Override
    protected ParsingState evalLineFeed() {
        return null;
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        return null;
    }

    @Override
    protected ParsingState evalSeparator() {
        return null;
    }

    @Override
    protected ParsingState evalQuotes() {
        return null;
    }
}
