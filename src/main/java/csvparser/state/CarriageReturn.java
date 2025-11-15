package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(StringBuilder stringBuilder) {
        super(stringBuilder);
    }

    @Override
    public ParsingState evalCharacter(char character) {
        if(character == '\n') {
            return new RowEnd(stringBuilder);
        }

        throw new UnexpectedCharacterException(character, "Expected LF character.");
    }
}
