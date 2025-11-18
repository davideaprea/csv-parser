package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    public Escaping(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(rowBuilder.isSeparator(character)) {
            rowBuilder.buildColumn();

            return new ColumnStart(rowBuilder);
        }

        if(Character.isWhitespace(character)) {
            return new OutQuoted(rowBuilder);
        }

        if(character == '\r') {
            return new CarriageReturn(rowBuilder);
        }

        if(character == '"') {
            rowBuilder.addCharacter(character);

            return new InQuoted(rowBuilder);
        }

        throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
    }
}
