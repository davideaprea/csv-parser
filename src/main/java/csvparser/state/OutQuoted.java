package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    public OutQuoted(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n' || !Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }

        if (rowBuilder.isSeparator(character)) {
            return new ColumnEnd(rowBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(rowBuilder);
        }

        return this;
    }
}
