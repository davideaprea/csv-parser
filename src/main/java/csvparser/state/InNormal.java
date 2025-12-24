package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

class InNormal extends ParsingState {
    protected InNormal(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState = this;

        if (character == '"' || character == '\n') {
            throw new UnexpectedCharacterException(character, "This character can't appear in a non-quoted field.");
        } else if (character == context.separator().symbol) {
            context.gridBuilder().endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else {
            context.gridBuilder().appendToColumn(character);
        }

        return nextState;
    }
}
