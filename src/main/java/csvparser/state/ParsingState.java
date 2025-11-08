package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;

public abstract class ParsingState {
    private final CSVColumnSeparator separator;

    protected final StringBuilder stringBuilder;

    protected ParsingState(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        this.separator = separator;
        this.stringBuilder = stringBuilder;
    }

    public void evalCharacter(char character) {
        if (character == '"') {
            evalQuotes();
        } else if (character == separator.symbol) {
            evalSeparator();
        } else if (character == '\r') {
            evalCarriageReturn();
        } else if (character == '\n') {
            evalLineFeed();
        } else if (Character.isWhitespace(character)) {
            evalWhiteSpace(character);
        } else {
            evalNormalCharacter(character);
        }
    }

    protected abstract ParsingState evalNormalCharacter(char character);

    protected abstract ParsingState evalWhiteSpace(char character);

    protected abstract ParsingState evalLineFeed();

    protected abstract ParsingState evalCarriageReturn();

    protected abstract ParsingState evalSeparator();

    protected abstract ParsingState evalQuotes();
}
