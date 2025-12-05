package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    public OutQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        ParsingState nextState = this;

        if (context.isSeparator(character)) {
            context.endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (!Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }

        context.changeState(nextState);
    }
}
