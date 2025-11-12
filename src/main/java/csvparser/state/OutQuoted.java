package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    protected OutQuoted(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        return this;
    }

    @Override
    protected ParsingState evalLineFeed() {
        throw new UnexpectedCharacterException('\r', "LF characters should only appear in quoted fields or after a CR character.");
    }

    @Override
    protected ParsingState evalCarriageReturn() {
        return new CarriageReturn(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalSeparator() {
        return new ColumnEnd(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalQuotes() {
        throw new UnexpectedCharacterException(
                '"',
                "Double quotes can only be be used to wrap a column field or escaping another double quotes character"
        );
    }
}
