package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

class InNormal extends ParsingState {
    public InNormal(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        }
        if (character == context.separator().symbol) {
            buildColumn();

            return new ColumnStart(context);
        }
        if (character == '\r') {
            return new CarriageReturn(context);
        }

        context.stringBuilder().append(character);

        return this;
    }
}
