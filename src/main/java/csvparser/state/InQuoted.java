package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class InQuoted extends ParsingState {
    protected InQuoted(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        stringBuilder.append(character);

        return this;
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        stringBuilder.append(character);

        return this;
    }

    @Override
    protected ParsingState evalLineFeed() {
        stringBuilder.append('\n');

        return this;
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        stringBuilder.append('\r');

        return this;
    }

    @Override
    protected ParsingState evalSeparator() {
        stringBuilder.append(separator.symbol);

        return this;
    }

    @Override
    protected ParsingState evalQuotes() {
        return new Escaping(separator, stringBuilder);
    }
}
