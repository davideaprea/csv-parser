package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class OutQuoted extends ParsingState {
    public OutQuoted(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        if (context.isSeparator(character)) {
            context.endColumn();
            context.changeState(new ColumnStart(context));
        } else if (character == '\r') {
            context.changeState(new CarriageReturn(context));
        } else if (!Character.isWhitespace(character)) {
            throw new UnexpectedCharacterException(character, "No values allowed after closed quoted field.");
        }
    }
}
