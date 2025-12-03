package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(final char character) {
        if (character == '"') {
            context.changeState(new InQuoted(context));
        } else if (context.isSeparator(character)) {
            context.endColumn();
        } else if (character == '\r') {
            context.changeState(new CarriageReturn(context));
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else {
            context.addCharacter(character);
            context.changeState(new InNormal(context));
        }
    }
}
