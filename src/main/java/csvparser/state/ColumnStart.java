package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(final char character) {
        ParsingState nextState = this;

        if (character == '"') {
            nextState = new InQuoted(context);
        } else if (context.isSeparator(character)) {
            context.endColumn();
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else {
            context.addCharacter(character);
            nextState = new InNormal(context);
        }

        context.changeState(nextState);
    }
}
