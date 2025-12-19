package csvparser.state;

import csvparser.exception.MalformedFileException;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(GridBuilder gridBuilder) {
        super(gridBuilder);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        gridBuilder.endColumn();
        gridBuilder.nextRow();

        return new RowInit(gridBuilder);
    }

    @Override
    public void end() {
        throw new MalformedFileException("Expected LF character");
    }
}
