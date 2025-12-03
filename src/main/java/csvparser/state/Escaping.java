package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    public Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public void eval(char character) {
        if (context.isSeparator(character)) {
            context.endColumn();
            context.changeState(new ColumnStart(context));
        } else if (character == '\r') {
            context.changeState(new CarriageReturn(context));
        } else if (Character.isWhitespace(character)) {
            context.changeState(new OutQuoted(context));
        } else if (character == '"') {
            context.addCharacter(character);
            context.changeState(new InQuoted(context));
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }
    }
}
