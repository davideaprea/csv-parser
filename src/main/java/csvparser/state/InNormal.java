package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    public InNormal(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }

        if (rowBuilder.isSeparator(character)) {
            rowBuilder.buildColumn();

            return new ColumnStart(rowBuilder);
        }

        if (character == '\r') {
            return new CarriageReturn(rowBuilder);
        }

        rowBuilder.addCharacter(character);

        return this;
    }
}
