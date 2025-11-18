package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(CSVRowBuilder rowBuilder) {
        super(rowBuilder);

        rowBuilder.resetColumn();
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if (character == '"') {
            rowBuilder.resetColumn();

            return new InQuoted(rowBuilder);
        }

        if (rowBuilder.isSeparator(character)) {
            rowBuilder.buildColumn();
            rowBuilder.resetColumn();

            return this;
        }

        if (character == '\r') {
            return new CarriageReturn(rowBuilder);
        }

        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        rowBuilder.addCharacter(character);

        if (Character.isWhitespace(character)) {
            return this;
        }

        return new InNormal(rowBuilder);
    }
}
