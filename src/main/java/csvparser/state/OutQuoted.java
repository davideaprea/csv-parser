package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    public OutQuoted(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (rowBuilder.isSeparator(character)) {
            rowBuilder.buildColumn();

            return new ColumnStart(rowBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(rowBuilder);
        }

        if (Character.isWhitespace(character)) {
            return this;
        }

        throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
    }
}
