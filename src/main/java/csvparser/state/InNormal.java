package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class InNormal extends ParsingState {
    public InNormal(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }
        if (context.isSeparator(character)) {
            context.endColumn();

            return new ColumnStart(context);
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }

        context.addCharacter(character);

        return this;
    }
}
