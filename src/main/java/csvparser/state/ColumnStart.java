package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    protected ColumnStart(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        ParsingState parsingState;

        if (character == '"') {
            parsingState = evalQuotes();
        } else if (character == separator.symbol) {
            parsingState = evalSeparator();
        } else if (character == '\r') {
            parsingState = evalCarriageReturn();
        } else if (character == '\n') {
            parsingState = evalLineFeed();
        } else if (Character.isWhitespace(character)) {
            parsingState = evalWhiteSpace(character);
        } else {
            parsingState = evalNormalCharacter(character);
        }

        return parsingState;
    }

    protected ParsingState evalNormalCharacter(char character) {
        stringBuilder.append(character);

        return new InNormal(separator, stringBuilder);
    }

    protected ParsingState evalWhiteSpace(char character) {
        stringBuilder.append(character);

        return this;
    }

    protected ParsingState evalLineFeed() {
        throw new UnexpectedCharacterException('\n', "LF characters should only appear in quoted fields or after a CR character.");
    }

    protected ParsingState evalCarriageReturn() {
        return new CarriageReturn(separator, stringBuilder);
    }

    protected ParsingState evalSeparator() {
        return new ColumnEnd(separator, stringBuilder);
    }

    protected ParsingState evalQuotes() {
        stringBuilder.setLength(0);

        return new InQuoted(separator, stringBuilder);
    }
}
