package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    public InNormal(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        ParsingState nextState = this;

        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (context.isSeparator(character)) {
            context.endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else {
            context.addCharacter(character);
        }

        context.changeState(nextState);
    }
}
