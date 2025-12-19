package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class ColumnStart extends ParsingState {
    protected ColumnStart(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(final char character) {
        ParsingState nextState = this;

        if (character == '"') {
            nextState = new InQuoted(context);
        } else if (character == context.separator().symbol) {
            context.gridBuilder().endColumn();
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '\n') {
            throw new UnexpectedCharacterException(character, "LF characters should only appear in quoted fields or after a CR character.");
        } else {
            context.gridBuilder().appendToColumn(character);
            nextState = new InNormal(context);
        }

        return nextState;
    }
}
