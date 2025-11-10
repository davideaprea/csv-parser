package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        throw new UnexpectedCharacterException(character, "Expected LF character.");
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        throw new UnexpectedCharacterException(character, "Expected LF character.");
    }

    @Override
    protected ParsingState evalLineFeed() {
        return new RowEnd(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        throw new UnexpectedCharacterException('\r', "Expected LF character.");
    }

    @Override
    protected ParsingState evalSeparator() {
        throw new UnexpectedCharacterException(separator.symbol, "Expected LF character.");
    }

    @Override
    protected ParsingState evalQuotes() {
        throw new UnexpectedCharacterException('"', "Expected LF character.");
    }
}
