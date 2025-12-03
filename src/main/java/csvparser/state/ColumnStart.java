package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    public ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(final char character) {
        if (character == '"') {
            return new InQuoted(context);
        }
        if (context.isSeparator(character)) {
            context.endColumn();

            return this;
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }
        if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        }

        context.addCharacter(character);

        return new InNormal(context);
    }
}
