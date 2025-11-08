package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    protected ColumnStart(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        stringBuilder.append(character);

        return new InNormal(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        stringBuilder.append(character);

        return this;
    }

    @Override
    protected ParsingState evalLineFeed() {
        return new CarriageReturn(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        throw new UnexpectedCharacterException('\r', "LF characters should only appear in quoted fields or after a CR character.");
    }

    @Override
    protected ParsingState evalSeparator() {
        return new ColumnEnd(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalQuotes() {
        stringBuilder.setLength(0);

        return new InQuoted(separator, stringBuilder);
    }
}
