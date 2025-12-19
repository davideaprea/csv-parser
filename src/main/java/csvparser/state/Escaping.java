package csvparser.state;

import csvparser.exception.UnexpectedCharacterException;

public class Escaping extends ParsingState {
    protected Escaping(ParsingContext context) {
        super(context);
    }

    @Override
    public ParsingState eval(char character) {
        ParsingState nextState;

        if (character == context.separator().symbol) {
            context.gridBuilder().endColumn();
            nextState = new ColumnStart(context);
        } else if (character == '\r') {
            nextState = new CarriageReturn(context);
        } else if (character == '"') {
            context.gridBuilder().appendToColumn(character);
            nextState = new InQuoted(context);
        } else {
            throw new UnexpectedCharacterException(character, "Found invalid character for escaping.");
        }

        return nextState;
    }

    @Override
    public void end() {
        context.gridBuilder().endColumn();
    }
}
