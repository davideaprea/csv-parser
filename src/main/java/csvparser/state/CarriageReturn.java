package csvparser.state;

import csvparser.exception.MalformedFileException;
import csvparser.exception.UnexpectedCharacterException;

public class CarriageReturn extends ParsingState {
    public CarriageReturn(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        if (character != '\n') {
            throw new UnexpectedCharacterException(character, "Expected LF character.");
        }

        context.endColumn();
        context.addRow();
        context.changeState(new Null(context));
    }

    @Override
    public void end() {
        throw new MalformedFileException("Expected LF character");
    }
}
