package csvparser.state;

import csvparser.exception.MalformedFileException;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    protected CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        context.gridBuilder().endColumn();
        context.gridBuilder().nextRow();

        return new RowInit(context);
    }

    @Override
    public void end() {
        throw new MalformedFileException("Expected LF character");
    }
}
