package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    public Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        ParsingState nextState;

        if (context.isSeparator(character)) {
            context.endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '"') {
            context.addCharacter(character);
            nextState = new InQuoted(context);
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }

        context.changeState(nextState);
    }

    @Override
    public void end() {
        context.endColumn();
    }
}
