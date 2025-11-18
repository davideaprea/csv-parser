package csvparser.state;

import csvparser.builder.CSVRowBuilder;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    public CarriageReturn(CSVRowBuilder rowBuilder) {
        super(rowBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '\n') {
            return new RowEnd(rowBuilder);
        }

        throw new UnexpectedCharacterException(character, "Expected LF character.");
    }
}
