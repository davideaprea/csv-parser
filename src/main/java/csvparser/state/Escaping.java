package csvparser.state;

import csvparser.enumeration.CSVColumnSeparator;
import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(CSVColumnSeparator separator, StringBuilder stringBuilder) {
        super(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalNormalCharacter(char character) {
        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }

    @Override
    protected ParsingState evalWhiteSpace(char character) {
        return new OutQuoted(separator, stringBuilder);
    }

    @Override
    protected ParsingState evalLineFeed() {
        throw new UnexpectedCharacterException('\n', "Found invalid character for escaping.");
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
        stringBuilder.append('"');

        return new InQuoted(separator, stringBuilder);
    }
}
