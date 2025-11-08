package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public class ColumnStart extends ParsingState {
    protected ColumnStart(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected void evalNormalCharacter(char character) {
        stringBuilder.append(character);

        return
    }

    @Override
    protected void evalWhiteSpace(char character) {

    }

    @Override
    protected void evalLineFeed() {

    }

    @Override
    protected void evalCarriageReturn() {

    }

    @Override
    protected void evalSeparator() {

    }

    @Override
    protected void evalQuotes() {

    }
}
